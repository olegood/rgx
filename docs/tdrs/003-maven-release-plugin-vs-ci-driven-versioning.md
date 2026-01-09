# 003: Maven Release Plugin vs. CI-driven Versioning

**Status:** Accepted \
**Date:** Dec 31, 2025 \
**Decision Owner:** Engineering Team

### Context

The team needs to decide how to manage versioning and releases for a Maven-based Java application. The release process
must produce versioned Maven artifacts and package those artifacts into Docker container images. The Docker image tag
must match the artifact version used at build time.

A key constraint is that the **version declared in the POM must be a concrete, final version during the build**.
Approaches that rely on placeholder versions overridden at build time introduce ambiguity when building and tagging
Docker images and complicate traceability, reproducibility, and debugging.

The decision is evaluated primarily on:

- Deterministic and reproducible releases
- Alignment between Maven artifact versions and Docker image tags
- Operational simplicity and standardization
- Suitability for CI-based releases

## Options Considered

### Option A – Maven Release Plugin

The Maven Release Plugin manages the full release lifecycle:

- Converts `-SNAPSHOT` versions into release versions
- Commits version changes
- Creates SCM tags
- Increments the next development version
- Builds and deploys artifacts from the tagged source state

Docker images can be built during the release process using the resolved Maven project version.

### Option B – CI-derived Versioning from Git Tags

The POM contains a fixed placeholder version, and the effective version is derived from Git tags at build time. CI
pipelines inject the computed version into the build and tag Docker images accordingly.

### Option C – Maven Versions Plugin with Scripted Version Bumps

Version changes are automated using the Maven Versions Plugin combined with custom scripts. Scripts compute the next
version, update the POM, build artifacts, tag SCM, and increment back to the next development version.

### Option D – Conventional Commits with semantic-release-style Tooling

Versions are derived automatically from commit messages following the Conventional Commits specification. Releases are
computed in CI, Git tags are created automatically, and Maven builds override the project version at build time without
committing version changes.

### Option E – Revision-based Versioning (`${revision}` Property)

The POM declares a version property such as `${revision}`. CI pipelines override the property with a release version
during the build while keeping the repository version unchanged.

## Comparison

### Version Authority and Docker Compatibility

The Maven Release Plugin ensures that the POM version is the authoritative version during the build. This aligns
naturally with Docker image creation, where the resolved project version can be used directly as the image tag.

All other options rely on indirection: placeholder versions, build-time overrides, or external scripts. These approaches
complicate Docker image tagging and weaken the link between source state, artifact metadata, and container images.

### Determinism and Reproducibility

With the Maven Release Plugin, a release can be reproduced by checking out the release tag and rebuilding. Version
changes are explicit and committed, and the release build operates on immutable source state.

CI-derived and semantic-release-style approaches depend on external logic and historical context to recompute versions,
making local reproduction and post-hoc analysis more difficult.

### Operational Complexity

The Maven Release Plugin follows a well-known, standardized Maven workflow with clear failure and rollback semantics.

Scripted or semantic-release-based approaches introduce additional tooling, scripting, and conventions that must be
maintained and understood by the team. While powerful, they increase cognitive and operational overhead.

### Automation and Discipline

Conventional Commits–based solutions provide a high degree of automation but require strict adherence to commit message
discipline across all contributors. Incorrect commit messages can lead to incorrect versioning decisions.

The Maven Release Plugin requires more deliberate release actions but is less sensitive to individual commit message
quality.

## Decision

The team will use the **Maven Release Plugin** as the standard mechanism for releasing the application.

The release process will rely on the plugin’s default versioning behavior and will build Docker images from the release
tag, ensuring alignment between Maven artifact versions, Docker image tags, and SCM tags.

## Rationale

The Maven Release Plugin best satisfies the requirement that the **POM version must be concrete and authoritative at
build time**, which is essential for consistent Docker image packaging.

While more automated alternatives exist, they rely on placeholder versions or external version computation, which
conflicts with the project’s containerization and traceability requirements. The Maven Release Plugin provides a clear,
deterministic, and auditable release process with minimal custom logic.

## Consequences

- Version changes will be committed to source control.
- Releases require an explicit release step rather than being fully implicit.
- The release process is predictable, reproducible, and aligned across Maven artifacts and Docker images.
- Additional automation (for example, semantic version inference) is intentionally traded off for clarity and
  reliability.

## Notes

Alternative approaches such as Conventional Commits–based semantic releases may be reconsidered if future requirements
allow Docker image tags to be decoupled from Maven artifact versions or if placeholder-based versioning becomes
acceptable.

This decision applies to production releases that produce externally consumed artifacts and container images.
