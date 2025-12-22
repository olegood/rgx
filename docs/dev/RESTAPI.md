# Developer's Guide: REST API Naming Conventions & Best Practices

## Overview

This guide defines **mandatory conventions** for designing and naming REST APIs in this project. Consistent API naming
improves **clarity, usability, backward compatibility, and long-term maintainability**.

APIs are contracts. Poor naming decisions are expensive to fix later.

---

## Core Principles

When designing APIs, always aim for:

- **Consistency** – similar concepts are named the same way everywhere
- **Clarity** – endpoints should be self-explanatory
- **Stability** – names should survive future feature growth
- **Resource-orientation** – APIs model resources, not actions

---

## Use Nouns, Not Verbs

### ✅ Correct (Resource-Based)

~~~
GET /users
GET /orders/123
~~~

### ❌ Incorrect (Action-Based)

~~~
GET /getUsers
POST /createOrder
~~~

HTTP methods already describe the action.

---

## Plural vs. Singular Resources

### Rule: **Use plural nouns for resource collections**

Plural naming clearly represents collections and scales naturally.

### Examples

| Operation    | Endpoint                 |
|--------------|--------------------------|
| List users   | `GET /users`             |
| Get one user | `GET /users/{userId}`    |
| Create user  | `POST /users`            |
| Update user  | `PUT /users/{userId}`    |
| Delete user  | `DELETE /users/{userId}` |

❌ Avoid singular resources:

~~~
/user
/order
~~~

---

## Nested Resources

Use nesting **only when there is a strong ownership relationship**.

### Example

~~~
GET /users/{userId}/orders
GET /users/{userId}/orders/{orderId}
~~~

### Avoid Deep Nesting

❌ Too deep:

~~~
/users/{userId}/orders/{orderId}/items/{itemId}/details
~~~

Prefer flattening when possible:

~~~
/order-items/{itemId}
~~~

---

## Use Kebab-Case for URLs

### Rule

- Use **lowercase**
- Separate words with **hyphens**
- Never use camelCase or snake_case in URLs

### Example

~~~
/payment-methods
/order-items
~~~

---

## Use Consistent Resource Names

Choose one term and stick to it.

❌ Inconsistent:

~~~
/users
/customers/{id}
~~~

✅ Consistent:

~~~
/users
/users/{id}
~~~

---

## HTTP Methods Semantics

| Method | Purpose                   |
|--------|---------------------------|
| GET    | Retrieve resource(s)      |
| POST   | Create new resource       |
| PUT    | Replace entire resource   |
| PATCH  | Partially update resource |
| DELETE | Remove resource           |

---

## Filtering, Sorting & Pagination

### Filtering

Use query parameters:

~~~
GET /orders?status=PAID
~~~

### Sorting

~~~
GET /orders?sort=createdAt,desc
~~~

### Pagination

~~~
GET /orders?page=0&size=20
~~~

---

## Searching

For complex searches, prefer query parameters:

~~~
GET /users?email=john@example.com
~~~

If search logic becomes complex:

~~~
POST /users/search
~~~

---

## Actions That Don't Fit CRUD

When an operation is **not a simple CRUD**, use a **sub-resource**.

### Example

~~~
POST /orders/{orderId}/cancel
POST /users/{userId}/reset-password
~~~

Rules:

- Use verbs **only** for such exceptional cases
- Keep them explicit and limited

---

## API Versioning

### Rule: Version in the URL

~~~
/api/v1/users
/api/v2/users
~~~

Do **not** version by:

- Headers
- Query parameters

---

## Naming Path Variables

Use clear, descriptive identifiers.

~~~
/users/{userId}
/orders/{orderId}
~~~

❌ Avoid:

~~~
/users/{id}
~~~

---

## Use Standard HTTP Status Codes

| Status | Meaning               |
|--------|-----------------------|
| 200    | OK                    |
| 201    | Created               |
| 204    | No Content            |
| 400    | Bad Request           |
| 401    | Unauthorized          |
| 403    | Forbidden             |
| 404    | Not Found             |
| 409    | Conflict              |
| 500    | Internal Server Error |

---

## Error Response Structure

Keep errors predictable.

```json
{
  "code": "USER_NOT_FOUND",
  "message": "User with id 42 not found"
}
```

## Avoid Technical Leakage

❌ Bad:

~~~
/user-entity
/user-dto
~~~

✅ Good:

~~~
/users
~~~

APIs represent **business concepts**, not implementation details.

---

## Backward Compatibility Rules

- Never rename or remove fields without a new API version
- New fields must be optional
- Behavior changes require version bumps

## Examples of Well-Designed APIs

~~~
GET    /api/v1/users
POST   /api/v1/users
GET    /api/v1/users/{userId}
PATCH  /api/v1/users/{userId}
DELETE /api/v1/users/{userId}

GET    /api/v1/users/{userId}/orders
POST   /api/v1/orders/{orderId}/cancel
~~~

## Anti-Patterns to Avoid

- ❌ Verbs in URLs
- ❌ Singular resource names
- ❌ Deeply nested paths
- ❌ Inconsistent terminology
- ❌ Exposing database structure

## Summary

Good API naming is about **clear communication**.

By following these rules, we achieve:

- Predictable APIs
- Easier onboarding
- Fewer breaking changes
- Happier consumers

**Design APIs as if you can never change them—because one day, you won’t be able to.**
