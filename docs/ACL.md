# ACL (Spring Security ACL)

## ACLs are applied on:

- Project
- Document
- Attachment

## Why ACL?

RBAC alone is not enough because:

- A user may access _only certain Projects_
- A manager may approve _only documents in specific Projects_
- Auditors may see _only specific Organizations_

## Custom ACL Permissions

Besides standard permissions (`READ`, `WRITE`, `DELETE`, `ADMINISTRATION`), there are **custom permissions**.

### Custom ACL Permissions:

| Permission       | Meaning               |
|------------------|-----------------------|
| `APPROVE`        | Approve document      |
| `REJECT`         | Reject document       |
| `ARCHIVE`        | Archive document      |
| `ATTACH`         | Upload attachments    |
| `VIEW_SENSITIVE` | View redacted content |

### ACL Inheritance

- **Organization ACL** → inherited by Projects
- **Project ACL** → inherited by Documents
- **Document ACL** → inherited by Attachments

Example:

> If a user has `READ` on Project → can read all Documents under it.

## ACL Permission Mask Design

Low bits are reserved for standard permissions and higher bits for domain-specific ones.

### Mask Layout

| Bit | Hex     | Permission       | Meaning                |
|-----|---------|------------------|------------------------|
| 0   | `0x01`  | `READ`           | Read object            |
| 1   | `0x02`  | `WRITE`          | Modify object          |
| 2   | `0x04`  | `CREATE`         | Create child object    |
| 3   | `0x08`  | `DELETE`         | Delete object          |
| 4   | `0x10`  | `ADMIN`          | Change ACL             |
| 5   | `0x20`  | `APPROVE`        | Approve document       |
| 6   | `0x40`  | `REJECT`         | Reject document        |
| 7   | `0x80`  | `ARCHIVE`        | Archive document       |
| 8   | `0x100` | `ATTACH`         | Add attachments        |
| 9   | `0x200` | `VIEW_SENSITIVE` | View restricted fields |

> This keeps compatibility with `BasePermission` while extending it.

## ACL Inheritance Rules

### Inheritance Graph

~~~
Organization (inherits = true)
 └── Project (inherits = true)
     └── Document (inherits = true)
         └── Attachment (inherits = true)
~~~

### Inheritance Semantics

| Parent Permission | Child Effect                          |
|-------------------|---------------------------------------|
| `READ`            | Can read all descendants              |
| `WRITE`           | Can edit descendants                  |
| `CREATE`          | Can create child objects              |
| `ADMIN`           | Can manage ACLs on children           |
| `APPROVE`         | Applies only to Document              |
| `ATTACH`          | Applies only to Document & Attachment |

### ACL Permission Matrix

#### Organization

| Role      | `READ` | `WRITE` | `CREATE` | `DELETE` | `ADMIN` |
|-----------|--------|---------|----------|----------|---------|
| `USER`    | ❌      | ❌       | ❌        | ❌        | ❌       |
| `MANAGER` | ✔      | ❌       | ✔        | ❌        | ❌       |
| `ADMIN`   | ✔      | ✔       | ✔        | ✔        | ✔       |
| `AUDITOR` | ✔      | ❌       | ❌        | ❌        | ❌       |

#### Project

| Role      | `READ` | `WRITE` | `CREATE` | `DELETE` | `ADMIN` |
|-----------|--------|---------|----------|----------|---------|
| `USER`    | ✔      | ✔       | ✔        | ❌        | ❌       |
| `MANAGER` | ✔      | ✔       | ✔        | ✔        | ❌       |
| `ADMIN`   | ✔      | ✔       | ✔        | ✔        | ✔       |
| `AUDITOR` | ✔      | ❌       | ❌        | ❌        | ❌       |

#### Document

| Role      | `READ`  | `WRITE` | `DELETE` | `APPROVE` | `REJECT` | `ARCHIVE` | `ATTACH` | `VIEW_SENSITIVE` |
|-----------|---------|---------|----------|-----------|----------|-----------|----------|------------------|
| `USER`    | ✔ (own) | ✔ (own) | ❌        | ❌         | ❌        | ❌         | ✔        | ❌                |
| `MANAGER` | ✔       | ✔       | ❌        | ✔         | ✔        | ✔         | ✔        | ✔                |
| `ADMIN`   | ✔       | ✔       | ✔        | ✔         | ✔        | ✔         | ✔        | ✔                |
| `AUDITOR` | ✔*      | ❌       | ❌        | ❌         | ❌        | ❌         | ❌        | ❌                |

\* Auditor can read only `APPROVED` / `ARCHIVED`

#### Attachment

| Role      | `READ` | `WRITE` | `DELETE` |
|-----------|--------|---------|----------|
| `USER`    | ✔      | ❌       | ❌        |
| `MANAGER` | ✔      | ✔       | ❌        |
| `ADMIN`   | ✔      | ✔       | ✔        |
| `AUDITOR` | ✔      | ❌       | ❌        |

### Permission Assignment Strategy

#### On Creation

~~~
Owner:
  READ, WRITE, ATTACH

Manager Role (via Project ACL):
  READ, APPROVE, REJECT

Auditor Role:
  READ (conditional)
~~~
