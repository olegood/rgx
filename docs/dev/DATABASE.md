# Developer's Guide: Database Object Naming Conventions

## Overview

This document defines **mandatory naming conventions** for all database objects used in this project.  
Consistent naming ensures:

- Readability across teams and tools
- Easier schema evolution
- Safer migrations
- Better collaboration between application and database engineers

**Database names are long-lived contracts. Poor names are expensive to fix.**

---

## General Naming Rules

These rules apply to **all database objects**.

### Mandatory Rules

- Use **UPPERCASE** for all database identifiers
- Use **snake_case** (underscores between words)
- Use **singular nouns** for tables
- Use **clear, descriptive names**
- Avoid abbreviations unless industry-standard
- Never use SQL reserved keywords

### Example

| Good            | Bad           |
|-----------------|---------------|
| `USER_ACCOUNT`  | `userAccount` |
| `ORDER_ITEM`    | `order-items` |
| `CREATED_AT`    | `createdAt`   |
| `EMAIL_ADDRESS` | `EMAIL`       |

---

## Table Naming Conventions

### Rules

- Use **singular nouns**
- Represent one business entity per table
- Avoid prefixes like `TBL_`

### Pattern

~~~
ENTITY_NAME
~~~

### Examples

| Good             | Bad             |
|------------------|-----------------|
| `USER`           | `USERS`         |
| `ORDER`          | `ORDER_TABLE`   |
| `PAYMENT_METHOD` | `PAYMENTMETHOD` |

---

## Column Naming Conventions

### Rules

- Use **UPPERCASE snake_case**
- Avoid table name repetition
- Be explicit and descriptive
- Use consistent suffixes

### Common Suffixes

| Purpose    | Suffix       |
|------------|--------------|
| Identifier | `_ID`        |
| Timestamp  | `_AT`        |
| Date       | `_DATE`      |
| Boolean    | `IS_` prefix |
| Count      | `_COUNT`     |

### Examples

| Good            | Bad           |
|-----------------|---------------|
| `USER_ID`       | `ID`          |
| `CREATED_AT`    | `CREATE_TIME` |
| `IS_ACTIVE`     | `ACTIVE`      |
| `EMAIL_ADDRESS` | `EMAIL`       |

---

## Primary Key Naming

### Rules

- Single-column surrogate keys preferred
- Always named `ID`
- Type should be numeric or UUID

### Example

```sql
ID BIGINT NOT NULL
```

---

## Foreign Key Column Naming

### Pattern

~~~
{REFERENCED_TABLE}_ID
~~~

### Example

```sql
USER_ID BIGINT NOT NULL
```

Avoid ambiguous names:

~~~
ID_USER ❌
USERID ❌
~~~

---

## Constraint Naming Conventions

**Always explicitly name constraints. Never rely on auto-generated names.**

### Primary Key

~~~
PK_{TABLE}
~~~

Example:

~~~
PK_USER
~~~

---

### Foreign Key

~~~
FK_{TABLE}_{COLUMN}
~~~

Example:

~~~
FK_ORDER_USER_ID
~~~

---

### Unique Constraint

~~~
UK_{TABLE}_{COLUMN}
~~~

Example:

~~~
UK_USER_EMAIL_ADDRESS
~~~

---

### Check Constraint

~~~
CK_{TABLE}_{RULE}
~~~

Example:

~~~
CK_ORDER_STATUS
~~~

---

## Index Naming Conventions

### Pattern

~~~
IX_{TABLE}_{COLUMN}
~~~

Examples

~~~
IX_USER_EMAIL_ADDRESS
IX_ORDER_CREATED_AT
~~~

For composite indexes:

~~~
IX_ORDER_USER_ID_CREATED_AT
~~~

---

## Sequence Naming (If Applicable)

### Pattern

~~~
SEQ_{TABLE}
~~~

Example:

~~~
SEQ_USER
~~~

---

## View Naming Conventions

### Rules

- Prefix with `V_`
- Name describes the projection

### Pattern

~~~
V_{DESCRIPTION}
~~~

Example:

~~~
V_ACTIVE_USER
~~~

---

## Audit Columns (Standardized)

All tables should include:

```sql
CREATED_AT TIMESTAMP NOT NULL
CREATED_BY VARCHAR(100) NOT NULL
UPDATED_AT TIMESTAMP
UPDATED_BY VARCHAR(100)
```

This ensures traceability and consistency.

---

## Boolean Columns

### Rules

- Always use `IS_` prefix
- Store as BOOLEAN (or equivalent)

Examples

| Good         | Bad           |
|--------------|---------------|
| `IS_ACTIVE`  | `ACTIVE_FLAG` |
| `IS_DELETED` | `DELETED`     |

---

### Avoid These Anti-Patterns

❌ Abbreviations:

~~~
USR
ORD
~~~

❌ Mixed case:

~~~
User
OrderItem
~~~

❌ Meaningless names:

~~~
DATA
VALUE
FIELD1
~~~

❌ Technical leakage:

~~~
USER_ENTITY
ORDER_DTO
~~~

---

## Schema Evolution Rules

- Never rename columns without migration
- Never change meaning of a column
- Deprecate before removal
- Backward compatibility is mandatory

---

## Summary

By following these naming conventions, we achieve:

- Predictable schemas
- Easier onboarding
- Safer migrations
- Cleaner Liquibase scripts
- Clear communication between teams

**Database naming is a design decision, not a formatting choice.**
**Get it right early—or pay for it forever.**
