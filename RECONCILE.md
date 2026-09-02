# Reconcile before merging to main

Scope: `integration/spring-boot-4.1`. This file is deleted by the merge commit — if it
still exists on `main`, the merge skipped these checks.

Run them after **every sync from main**, not just before the merge. Main keeps growing the
things this branch had to change, so each sync can re-import them.

## Reason phrases and status constants

RFC 9110 renamed the phrases and Spring 7 renamed the constants. Main still carries the old
spelling, so a sync re-imports it — `49140b5f` swept one that arrived that way.

```bash
git grep -F -c -e "Unprocessable Entity" -e "Unprocessible Entity" \
               -e "Payload Too Large" -- src ; # expect: no output
git grep -F -c -e "UNPROCESSABLE_ENTITY" -e "PAYLOAD_TOO_LARGE" -- src ; # expect: no output
```

## Version coordinate

`2.20.0-SBM-SNAPSHOT` is an integration label, not a release version.

```bash
grep -rn "SBM-SNAPSHOT" pom.xml ../core-spring-boot/pom.xml ; # expect: no output at merge
```

Both poms must move together — core pins the interfaces artifact, so changing one alone
breaks the downstream build.
