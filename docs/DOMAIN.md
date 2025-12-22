# Domain Model

## Core Hierarchy

~~~
Organization
 └── Project
     └── Document
         └── Attachment
~~~

## Domain Objects

### Organization

- id
- name
- status (active, suspended)

### Project

- id
- organization
- name
- compliance level (low, medium, high)

### Document

- id
- project
- title
- content
- status (draft, in review, approved, rejected, archived)
- created by
- approved by
- approved at

### Attachment

- id
- document
- file name
- checksum
- uploaded at

## User & Security

### User

- id
- username
- password (hashed)
- enabled
- locked
- created at

### Roles (RBAC)

| Role      | Description                  |
|-----------|------------------------------|
| `USER`    | Regular user                 |
| `MANAGER` | Approver / reviewer          |
| `ADMIN`   | User & system management     |
| `AUDITOR` | Read-only complicance access |

### RBAC Capabilities

#### USER

- Create Projects within the assigned organization
- Create & edit own regulatory documents (CRU)
- Upload attachments
- Cannot approve or reject

#### MANAGER

- All `USER` permissions
- Approve / reject documents
- Reassign reviewers
- Override document status

#### ADMIN

- Manage users
- Reset passwords
- Assign roles
- Assign organization / project access

#### AUDITOR

- Read-only access
- Only to `APPROVED` and `ARCHIVED` documents
- No attachments upload
