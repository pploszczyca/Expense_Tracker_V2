# Expense Tracker V2

Simple Expense Tracker Android application for managing your expenses/incomes.

## Technologies/Libraries

* Kotlin 2.0
* Jetpack Compose
* Coroutines
* Material Design 3
* Room Database
* Hilt
* OpenTelemetry
* Timber
* Kotest
* Mockk

## Architecture 
- Clean Architecture
  - Domain
    - Entities
    - Value Objects
  - Use Cases
  - Repository
  - Presentation
    - ViewModels
    - UI
- Feature Modules
  - Main
  - Add/Edit Expense
  - Statistics
  - Category Settings
- MVVM architecture
- Single Activity
- Multi-module
- Inversion of Control (IoC)

## Installation

Download:

```
$ git clone https://github.com/pploszczyca/Expense_Tracker_V2.git
```

And import Project by Android Studio Menu > File > Import project

## Workflows
The project uses GitHub Actions to automate the CI/CD process. The workflows are defined in the `.github/workflows` directory.
For now, the project has the following workflows:
- `build_debug_applications.yml` - builds the debug version of the application.
- `build_release_applications.yml` - builds the release version of the application.
- `run_tests.yml` - runs the tests.

## Features

- [X] Saving expenses/incomes,
- [X] Editing/Deleting expenses/incomes,
- [X] See current money status in wallet,
- [X] Filter data in main list menu by days,
- [X] Statistics in specific period of time
- [X] Search specific expense,
- [X] Adding categories for expenses/incomes,