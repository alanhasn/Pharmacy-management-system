# Pharmacy System

This is a simple pharmacy system to manage prescriptions and inventory. It is being used to practice [Separation of Concerns](https://en.wikipedia.org/wiki/Separation_of_concerns) and [Clean Architecture](https://blog.8thlight.com/uncle-bob/2012/08/13/The-Clean-Architecture.html), and [Coroutines](https://kotlinlang.org/docs/reference/coroutines.html) and is still under development.
## Features

*   Manage prescriptions
*   Manage inventory
*   Assign a pharmacist to a prescription

## How to Run

1.  Clone the repository
2.  Run `./gradlew build` to build the project
3.  Run `./gradlew run` to run the project

## Project Structure

The project is structured as follows:

*   `domain`: Contains all domain-related logic
*   `application`: Contains all application-related logic
*   `infrastructure`: Contains all infrastructure-related logic, such as repositories and logging
*   `util`: Contains utility functions