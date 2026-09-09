#!/usr/bin/env bash
# Local SonarQube analysis for interfaces (Java/Maven).
# Spins up an ephemeral SonarQube Community container, runs mvn verify with
# JaCoCo coverage, then mvn sonar:sonar against the local instance, and
# prints the quality gate + open issues.
# Usage: ./scripts/sonar-local.sh
#
# IMPORTANT — Limitation of ephemeral SonarQube:
# The SonarQube container is freshly started on every run, so it has no
# previous analysis to use as a "new code" baseline. The Quality Gate's
# new-code conditions therefore evaluate trivially OK and do NOT match the
# behaviour you'll see on SonarCloud (where a real baseline exists). To
# approximate PR-style focus locally, this script intersects the Sonar
# issue list with `git diff --name-only main...HEAD` and reports only
# issues on changed files. Use it as a smoke-check; treat SonarCloud on
# the actual PR as authoritative.
set -euo pipefail

CONTAINER_NAME="ilm-interfaces-sonarqube"
SONAR_PORT="${SONAR_PORT:-9000}"
PROJECT_KEY="ilm-interfaces"
SONAR_URL="http://localhost:${SONAR_PORT}"

cleanup() {
    echo "Stopping SonarQube..."
    docker rm -f "${CONTAINER_NAME}" >/dev/null 2>&1 || true
}
trap cleanup EXIT

docker rm -f "${CONTAINER_NAME}" >/dev/null 2>&1 || true

echo "Starting SonarQube Community on port ${SONAR_PORT}..."
docker run -d --name "${CONTAINER_NAME}" -p "${SONAR_PORT}:9000" sonarqube:community >/dev/null

echo "Waiting for SonarQube to be ready (up to 2 minutes)..."
for i in $(seq 1 120); do
    if curl -sf "${SONAR_URL}/api/system/status" 2>/dev/null | grep -q '"status":"UP"'; then
        echo "SonarQube is ready."
        break
    fi
    if [ "$i" -eq 120 ]; then
        echo "ERROR: SonarQube failed to start within 2 minutes."
        exit 1
    fi
    sleep 1
done

echo "Configuring SonarQube..."
# SonarQube ships with `admin/admin` as the factory default; the server forces
# this password to be changed on first login. We rotate it to `Admin12345678!`
# (a value that satisfies SonarQube's password policy) so the rest of the script
# can authenticate. Both credentials are scoped to this ephemeral container only —
# the container is removed by the EXIT trap, so neither value is persisted.
curl -s -o /dev/null -u admin:admin -X POST \
    "${SONAR_URL}/api/users/change_password?login=admin&previousPassword=admin&password=Admin12345678!" 2>/dev/null || true

if curl -sf -u admin:Admin12345678! "${SONAR_URL}/api/system/status" >/dev/null 2>&1; then
    SONAR_CREDS="admin:Admin12345678!"
elif curl -sf -u admin:admin "${SONAR_URL}/api/system/status" >/dev/null 2>&1; then
    SONAR_CREDS="admin:admin"
else
    echo "ERROR: Cannot authenticate to SonarQube."
    exit 1
fi

TOKEN_NAME="ci-$(date +%s)"
TOKEN=$(curl -sf -u "${SONAR_CREDS}" -X POST \
    "${SONAR_URL}/api/user_tokens/generate?name=${TOKEN_NAME}" \
    | python3 -c "import sys,json; print(json.load(sys.stdin)['token'])")

if [ -z "${TOKEN}" ]; then
    echo "ERROR: Failed to generate SonarQube token."
    exit 1
fi

echo "Running mvn verify with JaCoCo..."
mvn -B -U verify -Dmaven.compiler.proc=full

# Fully-qualified coordinates rather than the "sonar" prefix: prefix resolution walks the
# remote plugin groups, which fails when the private package registry answers 401.
echo "Running sonar:sonar..."
mvn -B org.sonarsource.scanner.maven:sonar-maven-plugin:5.7.0.6970:sonar \
    -Dmaven.compiler.proc=full \
    -Dsonar.projectKey="${PROJECT_KEY}" \
    -Dsonar.host.url="${SONAR_URL}" \
    -Dsonar.token="${TOKEN}" \
    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
    -Dsonar.cpd.minimumTokens=100

echo ""
echo "=== SonarQube Results ==="
echo "Dashboard: ${SONAR_URL}/dashboard?id=${PROJECT_KEY}"

# Sonar processes the analysis report asynchronously after upload. The scanner records the
# background task id, so wait for that task to finish: querying measures earlier returns an
# empty payload and a quality gate status of NONE.
echo ""
echo "Waiting for Sonar to process the analysis..."
CE_TASK_ID=$(sed -n 's/^ceTaskId=//p' target/sonar/report-task.txt 2>/dev/null || true)
if [ -z "${CE_TASK_ID}" ]; then
    echo "ERROR: no ceTaskId in target/sonar/report-task.txt; the analysis was not submitted."
    exit 1
fi
TASK_STATUS=""
for i in $(seq 1 90); do
    TASK_STATUS=$(curl -s -u "${SONAR_CREDS}" "${SONAR_URL}/api/ce/task?id=${CE_TASK_ID}" 2>/dev/null \
        | python3 -c "import sys,json; print(json.load(sys.stdin)['task']['status'])" 2>/dev/null || true)
    if [ "${TASK_STATUS}" = "SUCCESS" ]; then
        break
    fi
    if [ "${TASK_STATUS}" = "FAILED" ] || [ "${TASK_STATUS}" = "CANCELED" ]; then
        echo "ERROR: Sonar background task ${CE_TASK_ID} ended as ${TASK_STATUS}."
        exit 1
    fi
    sleep 2
done
if [ "${TASK_STATUS}" != "SUCCESS" ]; then
    # Reporting here would show the previous analysis, which is worse than showing nothing.
    echo "ERROR: Sonar background task ${CE_TASK_ID} did not finish within 3 minutes (last status: ${TASK_STATUS:-unknown})."
    exit 1
fi
PROBE=$(curl -s -u "${SONAR_CREDS}" \
    "${SONAR_URL}/api/qualitygates/project_status?projectKey=${PROJECT_KEY}" 2>/dev/null || true)

# Determine the set of files changed against the upstream base branch (main).
# We filter the issues report to those files only — ephemeral SonarQube has no
# previous analysis to use as a "new code" baseline, so we approximate
# PR-style focus by intersecting with the git diff.
BASE_BRANCH="${BASE_BRANCH:-main}"
CHANGED=$(git diff --name-only "${BASE_BRANCH}...HEAD" 2>/dev/null || true)
if [ -z "${CHANGED}" ]; then
    echo ""
    echo "(No changes vs ${BASE_BRANCH}; reporting full project for first-run baseline.)"
    SCOPE_DESC="all project files"
else
    echo ""
    echo "Files changed vs ${BASE_BRANCH}:"
    echo "${CHANGED}" | sed 's/^/  /'
    SCOPE_DESC="changed files only"
fi

echo ""
echo "================================================================================"
echo "EPHEMERAL SONARQUBE — LIMITATION"
echo "================================================================================"
echo "  This run uses a freshly-started SonarQube container with no previous"
echo "  analysis to act as a 'new code' baseline. The Quality Gate evaluates"
echo "  trivially OK and does NOT match SonarCloud, where a real baseline exists."
echo "  Treat this as a smoke check; SonarCloud on the actual PR is authoritative."
echo "================================================================================"

echo ""
echo "Quality Gate (whole project; new-code conditions trivially OK without baseline):"
echo "${PROBE}" | python3 -c "
import sys, json
try:
    d = json.load(sys.stdin)['projectStatus']
    print(f'  Status: {d[\"status\"]}')
    for c in d.get('conditions', []):
        print(f'  {c[\"metricKey\"]}: {c[\"actualValue\"]} (threshold: {c[\"errorThreshold\"]}) - {c[\"status\"]}')
except Exception as e:
    print(f'  WARN: Quality gate not yet available ({e}). See dashboard.')
"

echo ""
echo "Project measures (whole project, not new code):"
curl -s -u "${SONAR_CREDS}" \
    "${SONAR_URL}/api/measures/component?component=${PROJECT_KEY}&metricKeys=coverage,line_coverage,duplicated_lines_density,duplicated_blocks,ncloc" \
    | python3 -c "
import sys, json
labels = {
    'coverage': 'Coverage',
    'line_coverage': 'Line coverage',
    'duplicated_lines_density': 'Duplicated lines',
    'duplicated_blocks': 'Duplicated blocks',
    'ncloc': 'Lines of code',
}
try:
    measures = {m['metric']: m['value'] for m in json.load(sys.stdin)['component']['measures']}
    for metric, label in labels.items():
        if metric in measures:
            unit = '%' if metric in ('coverage', 'line_coverage', 'duplicated_lines_density') else ''
            print(f'  {label}: {measures[metric]}{unit}')
except Exception as e:
    print(f'  WARN: measures not available ({e}). See dashboard.')
"

echo ""
echo "Duplications — ${SCOPE_DESC}:"
curl -s -u "${SONAR_CREDS}" \
    "${SONAR_URL}/api/measures/component_tree?component=${PROJECT_KEY}&metricKeys=duplicated_lines&qualifiers=FIL&ps=500" \
    | CHANGED_LIST="${CHANGED}" python3 -c "
import sys, json, os
changed = [f.strip() for f in os.environ.get('CHANGED_LIST', '').splitlines() if f.strip()]
try:
    components = json.load(sys.stdin).get('components', [])
    rows = []
    for c in components:
        lines = next((int(m['value']) for m in c.get('measures', []) if m['metric'] == 'duplicated_lines'), 0)
        if lines and (not changed or any(c.get('path', '').endswith(f) for f in changed)):
            rows.append((lines, c.get('path')))
    if not rows:
        print('  None.')
    for lines, path in sorted(rows, reverse=True):
        print(f'  {lines} duplicated lines: {path}')
except Exception as e:
    print(f'  WARN: duplications not available ({e}). See dashboard.')
"

echo ""
echo "Issues — ${SCOPE_DESC} (filtered to git diff vs ${BASE_BRANCH}; not a full project view):"
curl -s -u "${SONAR_CREDS}" \
    "${SONAR_URL}/api/issues/search?projectKeys=${PROJECT_KEY}&statuses=OPEN&ps=500" \
    | CHANGED_LIST="${CHANGED}" python3 -c "
import sys, json, os
changed = [f.strip() for f in os.environ.get('CHANGED_LIST', '').splitlines() if f.strip()]
try:
    d = json.load(sys.stdin)
    issues = d.get('issues', [])
    if changed:
        # Keep only issues whose component path ends with one of the changed files
        issues = [i for i in issues if any(i.get('component', '').endswith(f) for f in changed)]
    print(f'  Total in scope: {len(issues)}')
    for i in issues[:50]:
        comp = i['component'].split(':')[-1]
        line = i.get('line', '?')
        print(f'  [{i[\"severity\"]}] {comp}:{line} - {i[\"message\"]}')
except Exception as e:
    print(f'  WARN: Issues list not available ({e}).')
"

echo ""
echo "Done. (Set BASE_BRANCH=<other> to compare against a different base; default: main.)"
