# 002: Exposing persistence models via Spring Data REST vs. manually implemented controllers

**Status:** Accepted \
**Date:** Dec 30, 2025 \
**Decision Owner:** Engineering Team

### Context

The team needs to decide how to expose CRUD-style HTTP APIs for domain data. Two approaches are under consideration:

1. Using Spring Data REST to automatically expose repositories as REST endpoints.
2. Implementing a traditional layered approach with explicit controllers, mappers (e.g., MapStruct), services, and
   repositories.

The decision is evaluated primarily on ease of implementation, maintainability over time, and ease of customization.

### Options Considered

#### Option A – Spring Data REST

Repositories are directly exposed as REST resources. Endpoint structure, pagination, sorting, and basic CRUD behavior
are generated automatically. Customization is achieved via repository annotations, projections, event handlers, and
limited controller extensions.

#### Option B – Traditional Controller–Mapper–Service Approach

REST controllers are explicitly implemented. Request/response models are mapped to domain entities via mappers. Business
logic is encapsulated in services, and repositories are used only internally.

### Comparison

#### Ease of Implementation

Spring Data REST provides rapid initial setup. A functional REST API can be created with minimal code by defining
entities and repositories. This is beneficial for prototypes, internal tools, and admin-style applications.

The traditional approach requires more upfront work: controllers, request/response models, mappers, and service classes
must be created explicitly. Initial development is slower, but the resulting structure is explicit and predictable.

### Maintainability

Spring Data REST tends to couple the API surface tightly to the persistence model. Over time, changes in entities or
repository behavior can unintentionally affect external contracts. Understanding the effective API often requires
knowledge of framework conventions and implicit behavior.

The traditional approach is more verbose but significantly clearer. API contracts, mappings, and business rules are
explicit and localized. This makes refactoring safer, onboarding easier, and long-term maintenance more predictable.

### Ease of Customization

Customization in Spring Data REST is possible but indirect. Non-trivial requirements—such as conditional behavior,
complex validation, workflow-driven endpoints, or backward-compatible API evolution—often require workarounds or
abandoning generated endpoints for manual controllers.

The traditional approach is highly customizable. Since controllers and mappers are under full control, adapting
endpoints, shaping responses, versioning APIs, or introducing complex orchestration logic is straightforward and
localized.

## Decision

The team will use the **traditional controller–mapper–service approach** for this application.

Spring Data REST will not be used to expose repositories directly.

## Rationale

Although Spring Data REST offers faster initial development, its implicit behavior and tight coupling between
persistence and API layers reduce long-term maintainability and flexibility. The application is expected to evolve in
terms of business logic, API shape, and integration requirements, making explicit control more valuable than early
convenience.

The traditional approach provides:

- Clear ownership of API contracts
- Strong separation between domain, application, and API layers
- Easier customization and safer refactoring over time

## Consequences

- More boilerplate code will be written initially.
- Development speed for simple CRUD endpoints may be slower.
- The codebase will be more explicit, easier to reason about, and more adaptable to future requirements.

## Notes

Spring Data REST may still be appropriate for:

- Rapid prototypes
- Internal administrative tools
- Read-only or low-change datasets

This decision applies to externally consumed and long-lived APIs.
