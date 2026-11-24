# Prism CLI

A console driven design system management application. Designed for frontend engineers and UI designers to manage, audit and export design tokens and UI components. 
Prism provides a centralised, relational terminal hub to maintain consistency between design primitives and code.  

## Current Project Status

The validation utilities, service layer CRUD/association logic, service unit tests, and the interactive **Token** and **Component** console menus in `main.kt` are in place. Interactive token–component linking is deferred to the next sprint/release, and full test verification is up next.

## Models

* **Token**: Represents a design token with `id`, `name`, `type` (`Type`), and `value`, overriding `toString()` to format `${type.display}` cleanly for display.
* **Component**: Represents a UI component with `id`, `name`, `category` (`Category`), `state` (`State`), `minWidth`, and `minHeight`, using `.display` formatting in `toString()`.
* **TokenComponent**: Represents a link between a token ID and a component ID managed via `TokenComponentService`.

## Project Structure

* **`model/`**:  (`Token`, `Component`, `TokenComponent`) and enums (`Type`, `Category`, `State`).
* **`utils/`**:
  * `UserInput.kt`: Console input helpers (`readNextInt`, `readNextLine`, `readNonBlankLine`).
  * `NumberUtilities.kt`: Numeric validation helpers (`validRange`, `isNonNegative`).
  * `EnumUtilities.kt`: Enum validation helpers and option sets.
  * `ValidateInput.kt`: Prompt-retry loops (`readValidType`, `readValidCategory`, `readValidState`, `readValidDimension`, etc.) backed by enum checks and numeric validation.
* **`service/`**:
  * `TokenService.kt`: Create, list, update, and delete tokens with auto-incrementing IDs starting at `1`.
  * `ComponentService.kt`: Create, list, update, and delete components with auto-incrementing IDs starting at `1`.
  * `TokenComponentService.kt`: Manage links/unlinks between components and tokens, block duplicate pairs, query linked IDs in both directions, and clean up links by token or component ID.
* **`main.kt`**: Interactive CLI menu with **Manage Tokens** and **Manage Components** CRUD flows, case-insensitive duplicate name checks, existence checks, and input validation.
* **`src/test/kotlin/`**: JUnit 5 test suites (`TokenServiceTest`, `ComponentServiceTest`, `TokenComponentServiceTest`

## Sample Console Outputs

### Adding and Listing Tokens
```text
=== Prism CLI ===
1. Manage Tokens
2. Manage Components
3. Manage Token–Component Links
0. Exit
Choose an option: 1

--- Tokens ---
1. Add Token
2. List Tokens
3. Update Token
4. Delete Token
0. Back

Choose an option: 1

Token name: brand-primary
Token type: Colour
Token value: #2563EB
Token added with ID 1.

Choose an option: 2

#1
Name: brand-primary
Type: Colour
Value: #2563EB
### Author
Jordan Butler
