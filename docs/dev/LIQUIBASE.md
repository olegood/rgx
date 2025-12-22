# Developer's Guide: Liquibase Best Practices

## Overview

This project uses **Liquibase** for database schema migrations. To ensure reliability, traceability, and long-term
maintainability of our database, **all changes must follow the conventions and best practices defined in this guide**.

Database migrations are part of our production codebase. Poorly written change logs can cause:

- Failed deployments
- Data loss
- Challenging rollbacks
- Fear of schema evolution

These guidelines exist to prevent that.

---

## Core Principles

When working with Liquibase, always aim for:

- **Predictability** – changes behave the same in every environment
- **Traceability** – every schema change has a clear purpose and owner
- **Reversibility** – every change can be rolled back safely
- **Atomicity** – each change does exactly one thing

---

## Rules

- Never put changes directly in `db.changelog-master.yaml`
- Group changes by feature or domain
- Keep files small and focused

---

## File Naming Conventions

Use **incremental numbering** and **descriptive names**.

### Pattern

~~~
NNN-short-description.yaml
~~~

### Examples

~~~
002-add-user-table.yaml
003-add-user-indexes.yaml
004-add-order-foreign-key.yaml
~~~

This ensures:

- Predictable execution order
- Easy navigation
- Clean diffs in code reviews

---

## ChangeSet Best Practices

### Keep ChangeSet Atomic

Each changeSet must represent one logical change.

❌ Bad:

```yaml
- changeSet:
    id: add-user-and-order
    author: dev
    changes:
      - createTable: ...
      - createTable: ...
```

✅ Good:

```yaml
- changeSet:
    id: add-user-table
    author: dev
    changes:
      - createTable: ...

```

### Always Write Meaningful Comments

Comments explain why the change exists.

```yaml
- changeSet:
    id: add-user-table
    author: dev
    comments: >
      Introduces the user table to store application users.
      Required for authentication and authorization features.
```

### Preconditions Are Mandatory

Preconditions prevent accidental failures and make migrations safer.

#### Example

```yaml
- changeSet:
    id: add-user-email-column
    author: dev
    preConditions:
      onFail: MARK_RAN
      not:
        columnExists:
          tableName: user
          columnName: email
    changes:
      - addColumn:
          tableName: user
          columns:
            - column:
                name: email
                type: varchar(255)
```

Rules

- Always define preconditions
- Use `MARK_RAN` when appropriate
- Prefer safety over strictness

### Rollbacks Are Not Optional

Every changeSet **must** define a rollback strategy.

#### Example

```yaml
- changeSet:
    id: add-user-table
    author: dev
    changes:
      - createTable:
          tableName: user
          columns:
            - column:
                name: id
                type: bigint
    rollback:
      - dropTable:
          tableName: user
```

If rollback is impossible, document it clearly and get approval.

### Naming Constraints Explicitly

Never rely on auto-generated constraint names.

#### Naming Patterns

| Constraint Type | Pattern               |
|-----------------|-----------------------|
| Primary Key     | `PK_{Table}`          |
| Foreign Key     | `FK_{Table}_{Column}` |
| Index           | `IX_{Table}_{Column}` |

#### Primary Key Example

```yaml
- changeSet:
    id: add-user-table
    author: dev
    changes:
      - createTable:
          tableName: user
          columns:
            - column:
                name: id
                type: bigint
                constraints:
                  primaryKey: true
                  primaryKeyName: PK_USER
                  nullable: false
```

#### Foreign Key Example

```yaml
- changeSet:
    id: add-order-user-fk
    author: dev
    changes:
      - addForeignKeyConstraint:
          baseTableName: order
          baseColumnNames: user_id
          referencedTableName: user
          referencedColumnNames: id
          constraintName: FK_ORDER_USER_ID
```

#### Index Example

```yaml
- changeSet:
    id: add-user-email-index
    author: dev
    changes:
      - createIndex:
          indexName: IX_USER_EMAIL
          tableName: user
          columns:
            - column:
                name: email
```

### Add Constraints and Indexes Immediately

Do not defer constraints or indexes.

**Why**

- Prevents invalid data
- Avoids performance regressions
- Keeps schema intent explicit

Always add:

- `NOT NULL`
- `UNIQUE`
- `FOREIGN KEY`
- Indexes for frequently queried columns

### One ChangeSet = One Deployment-Safe Step

If a change could:

- Lock tables
- Migrate large data
- Affect performance

Then:

- Split it into multiple changeSets
- Test on realistic data volumes

## Summary

Following these Liquibase best practices ensures:

- Safe database evolution
- Predictable deployments
- Easier troubleshooting
- Confidence in rollbacks

Database changes are permanent decisions.
Treat every Liquibase changeSet with the same care as production code.
