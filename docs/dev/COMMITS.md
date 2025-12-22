# Developer's Guide: Conventional Commits

## Overview

This project follows the **Conventional Commits** specification as the **mandatory standard** for all commit messages.
The goal is to keep our Version Control System (VCS) history **clean, readable, automatable, and future-proof**.

A consistent commit history is not just cosmetic—it directly improves collaboration, debugging, release automation, and
long-term maintainability.

---

## Why Conventional Commits Matter

### 1. Clean & Readable Git History

Conventional commits make it immediately clear **what changed and why**—without opening the diff.

Bad:

~~~
fix stuff
~~~

Good:

~~~
fix(auth): handle token expiration correctly
~~~

---

### 2. Faster Code Reviews

Reviewers can quickly:

- Understand intent
- Identify breaking changes
- Prioritize important commits

This reduces review time and improves feedback quality.

---

### 3. Automated Changelogs & Releases

Conventional commits enable tooling to:

- Generate changelogs automatically
- Determine semantic version bumps
- Trigger CI/CD workflows

Example mapping:

| Commit Type | Version Impact |
|-------------|----------------|
| fix         | Patch          |
| feat        | Minor          |
| BREAKING    | Major          |

---

### 4. Easier Debugging & Reverts

Clear commit intent allows:

- Faster `git bisect`
- Safe reverts
- Accurate blame analysis

---

### 5. Strong Team Discipline

A shared convention:

- Reduces ambiguity
- Sets clear expectations
- Scales well as the team grows

---

## Commit Message Format

~~~
<type>(<scope>): <short summary>

[optional body]

[optional footer(s)]
~~~

### Example

~~~
feat(order): add discount calculation for premium users

Applies a tier-based discount before tax calculation.

BREAKING CHANGE: Discount API now returns BigDecimal instead of double.
~~~

---

## Allowed Commit Types

| Type     | Usage                             |
|----------|-----------------------------------|
| feat     | New functionality                 |
| fix      | Bug fixes                         |
| chore    | Build tools, dependencies, config |
| docs     | Documentation changes             |
| style    | Formatting (no logic changes)     |
| refactor | Code restructuring                |
| test     | Adding or updating tests          |
| perf     | Performance improvements          |
| ci       | CI/CD related changes             |
| build    | Build system changes              |

---

## Scopes

Scopes provide **context** and should reference:

- Module name
- Service name
- Feature area

Examples:

~~~
feat(auth): add refresh token support

fix(payment): prevent duplicate transactions

refactor(api): simplify error handling
~~~

## Writing a Good Commit Message

### Best Practices

✅ Use **imperative mood**

~~~
add validation for email field
~~~

❌ Avoid past tense

~~~
added validation for email field
~~~

---

✅ Keep the subject line **under 72 characters**

---

✅ One logical change per commit  

Avoid mixing unrelated changes.

---

✅ Use the body to explain **why**, not **what**

The code already explains *what* changed.

---

## Breaking Changes

Breaking changes **must** be explicitly declared.

### Example

~~~
feat(user): replace username with email as identifier

BREAKING CHANGE: username field removed from User entity.
~~~

This ensures:

- Major version bumps are triggered
- Consumers are properly warned

---

## Examples of Good Commits

~~~
fix(cache): prevent stale entries on eviction

feat(reporting): export monthly report as CSV

refactor(security): centralize password hashing logic

docs(readme): document local development setup
~~~

---

## Examples of Bad Commits

❌ Too vague:

~~~
update code
~~~

❌ Multiple concerns:

~~~
fix bugs and add new feature
~~~

❌ No type:

~~~
payment issue resolved
~~~

---

## Enforcement

To ensure consistency:

- Commit messages **must** follow this format
- CI may reject non-conforming commits
- Squash merges must preserve conventional messages

Recommended tools:

- Commit hooks (commitlint)
- IDE commit templates
- Pull request checks

---

## Summary

Conventional Commits are not bureaucracy—they are **engineering leverage**.

They provide:

- Predictable history
- Automation readiness
- Better collaboration
- Lower long-term maintenance cost

**Every commit is documentation. Write it like it matters—because it does.**
