# Prism CLI

A console driven design system management application. Designed for frontend engineers and UI designers to manage, audit and export design tokens and UI components. 
Prism provides a centralised, relational terminal hub to maintain consistency between design primitives and code.  

## Current Project Status

The validation utilities, service layer CRUD/association logic, and service unit tests are in place. Next up is wiring everything into interactive console menus and domain checks in `main.kt`.

## Project Structure

* **`model/`**: Domain models (`Token`, `Component`, `TokenComponent`) 
* **`utils/`**:
  * `UserInput.kt`: Console input helpers (`readNextInt`, `readNextLine`, `readNonBlankLine`).
  * `NumberUtilities.kt`: Numeric validation helpers (`validRange`, `isNonNegative`).
  * `EnumUtilities.kt`: Enum validation helpers and option sets for `Category`, `State`, and `Type`.
  * `ValidateInput.kt`: Prompt-retry loops (`readValidCategory`, `readValidState`, `readValidType`, `readValidTokenValue`) backed by enum option sets and numeric checks.
* **`service/`**:
  * `ComponentService.kt`: Create, list, update, and delete components with auto-incrementing IDs starting at `1`.
  * `TokenService.kt`: Create, list, update, and delete tokens with auto-incrementing IDs starting at `1`.
  * `TokenComponentService.kt`: Manage links/unlinks between components and tokens, block duplicate pairs, query linked IDs in both directions, and clean up links by token or component ID.
* **`main.kt`**: Planned interactive CLI menu and domain/existence checks (currently being built).
* **`src/test/kotlin/`**: JUnit 5 test suites (`ComponentServiceTest`, `TokenServiceTest`, `TokenComponentServiceTest`, `NumberUtilitiesTest` and `EnumUtilitiesTest`).

### Author
Jordan Butler
