# tosan-validation

## Project Overview

Tosan Validation is a Java library for field validation using Hibernate Validator. It extends Jakarta Validation with custom validators for Iranian-specific validations (national codes, IBAN, mobile numbers, Jalali dates) and advanced validation features.

**Key Dependencies:**
- Java 17
- Hibernate Validator
- Jakarta Validation API
- jalali-calendar (for Persian date support)

## Architecture

### Core Validation Framework

The library builds on top of Jakarta Validation/Hibernate Validator with three key architectural patterns:

1. **Parameter Injection via Properties File**: Validators can reference dynamic values from a `validation-key-value.properties` file. This allows validation rules to be configurable without code changes.

2. **ValidatorBuilder Pattern**: Entry point for creating validator instances. Located in `com.tosan.validation.core.ValidatorBuilder`, it:
    - Loads parameters from properties files or maps
    - Configures a custom `CustomMessageInterpolator` for parameter substitution
    - Sets up `MapAwareValidatorFactory` to inject parameters into validators
    - Returns a fully configured Jakarta `Validator` instance

3. **Map-Aware Validation**: Support for validating Map structures dynamically using `@FieldDescriptor` annotations. The `MapAwareValidatorFactory` and `MapAwareValidator` base class enable validators to access external parameters and validator context.

### Package Structure

- **`com.tosan.validation.constraints`**: Annotation definitions for all custom validators
    - Iranian-specific: `@NationalCode`, `@Iban`, `@MobileNumber`, `@Pervasive`
    - Date validations: `@ValidateJalaliDate`, `@PastJalaliDate`, `@FutureJalaliDate`, `@DateDifference`
    - Advanced: `@Expression`, `@Compare`, `@ConditionalNotEmpty`, `@Group`

- **`com.tosan.validation.constraints.impl`**: Validator implementations
    - All validators extend either `BaseValidator` or implement standard Jakarta validation interfaces
    - Many validators inherit from `MapAwareValidator` to access external parameters

- **`com.tosan.validation.core`**: Core framework classes
    - `ValidatorBuilder`: Main entry point for creating validators
    - `MapAwareValidatorFactory`: Custom factory for injecting parameters
    - `FieldDescriptorExtractor*`: Classes for extracting validation metadata from Map structures
    - `CustomMessageInterpolator`: Substitutes parameter placeholders in error messages

- **`com.tosan.validation.util`**: Utilities
    - `ValidationConstraintViolationInfoTreeGenerator`: Generates hierarchical validation error structures
    - `ExpressionUtil`: SPEL expression evaluation for `@Expression` validators
    - `date/`: Date comparison utilities supporting multiple temporal types and Jalali dates

### Key Validation Features

1. **Expression-Based Validation**: `@Expression` and `@Expressions` use Spring Expression Language (SPEL) to evaluate complex conditional validation rules referencing other fields in the object.

2. **Field Comparison**: `@Compare` validates relationships between fields (e.g., endDate > startDate).

3. **Conditional Validation**: `@ConditionalNotEmpty` makes fields required based on conditions.

4. **Map Validation**: `@ValidMap` and `@ValidCollection` with `@FieldDescriptor` enable validation of dynamic Map/Collection structures where field names aren't known at compile time.

5. **Date Difference Validation**: `@DateDifference` validates time spans between two date fields with configurable units (days, months, years).

6. **Group Sequencing**: `@Group` allows conditional validation group execution based on field values.

## Testing

- Tests use TestNG framework (not JUnit)
- Test files are in `src/test/java/com/tosan/validation/`
- Test resources (including sample properties) are in `src/test/resource/`
- Most tests validate constraint violations using the standard Jakarta Validation API pattern

## Release Process

The project uses maven-release-plugin with:
- Tag format: `v{version}` (e.g., v5.2.5)
- Release profile for GitHub Packages deployment
- Build profile for Maven Central with GPG signing

## Important Notes

- Resources are in `src/main/resource` and `src/test/resource` (not the standard `resources` naming)
- The library expects a `validation-key-value.properties` file in the classpath for parameterized validations (optional, but enables key features)
- When creating new validators that need external parameters, extend `BaseValidator` or `MapAwareValidator`
- Custom validators requiring validator context should implement `ValidatorContextAwareConstraintValidator`
