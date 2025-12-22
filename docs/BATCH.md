# Batch Jobs

## Batch Job 1: Document Archival

- Runs nightly
- Archives documents approved > 1 year ago
- Removes write permissions
- Keeps read access for auditors

## Batch Job 2: Compliance Export

- Exports approved documents to CSV
- One file per Organization
- Only documents the user had access to at approval time

## Batch Job 3: ACL Cleanup

- Removes orphaned ACL entries
- Detects broken inheritance chains
