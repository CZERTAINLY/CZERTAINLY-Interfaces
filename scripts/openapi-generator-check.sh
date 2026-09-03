#!/usr/bin/env bash
# Runs the OpenAPI generators the platform depends on against a published document, so a contract change
# that breaks generated code fails here rather than in a consuming repository.
#
# Usage: ./scripts/openapi-generator-check.sh <document.json|document.yaml> [generator ...]
#
# The document is the one the platform serves, not something this repository builds: this repository holds the
# interfaces, and the service that implements them assembles the document from them. Fetch it first, for example
#   curl -o /tmp/core.json http://localhost:8080/v3/api-docs/core
# and point this script at the result.
#
# Generators default to the four the platform consumes: TypeScript for the administrator frontend, Go for the Go
# SDK, and Java and Python for connector authors. Requires Docker.
set -euo pipefail

if [ "$#" -lt 1 ]; then
    echo "Usage: $0 <document.json|document.yaml> [generator ...]" >&2
    exit 2
fi

DOCUMENT_PATH="$1"
shift
GENERATORS=("$@")
if [ "${#GENERATORS[@]}" -eq 0 ]; then
    GENERATORS=(typescript-axios go java python)
fi

if [ ! -f "${DOCUMENT_PATH}" ]; then
    echo "ERROR: no such document: ${DOCUMENT_PATH}" >&2
    exit 2
fi

# The generator image is pinned to the version the Go SDK generates with, so a result here means the same for it.
GENERATOR_IMAGE="openapitools/openapi-generator-cli:v7.22.0"

WORK_DIR="$(mktemp -d)"
trap 'rm -rf "${WORK_DIR}"' EXIT
cp "${DOCUMENT_PATH}" "${WORK_DIR}/document.${DOCUMENT_PATH##*.}"
DOCUMENT_NAME="document.${DOCUMENT_PATH##*.}"

echo "Document: ${DOCUMENT_PATH}"
echo "Generator: ${GENERATOR_IMAGE}"
echo ""

FAILED=()
for generator in "${GENERATORS[@]}"; do
    printf '%-20s ' "${generator}"
    # Validation stays on: a document that only generates with it disabled is a document that will break a consumer.
    if docker run --rm -v "${WORK_DIR}:/local" "${GENERATOR_IMAGE}" generate \
        -i "/local/${DOCUMENT_NAME}" -g "${generator}" -o "/local/out-${generator}" \
        > "${WORK_DIR}/${generator}.log" 2>&1; then
        echo "ok"
    else
        echo "FAILED"
        FAILED+=("${generator}")
        sed -n '/^Errors:/,/^$/p' "${WORK_DIR}/${generator}.log" | head -30
        grep -E '^\[error\]|Exception in thread' "${WORK_DIR}/${generator}.log" | head -10 || true
    fi
done

echo ""
if [ "${#FAILED[@]}" -eq 0 ]; then
    echo "All generators consumed the document without errors."
    exit 0
fi

echo "Generators that could not consume the document: ${FAILED[*]}"
exit 1
