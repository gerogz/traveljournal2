# Travel Journal

## Overview

Travel Journal is a dynamic travel journal application designed for travelers to document their trips, compose journal entries, and search for cities. The application offers a user-friendly GUI constructed using SceneBuilder and JavaFX. At its core, the application uses Java to connect to a MySQL relational database, which houses user information and journal entries associated with various trips.

## Features

- **User Authentication**: Register and login to gain access to your personal travel records.
- **Compose Entries**: Seamlessly add new trips, jot down experiences, and even add pictures.
- **Search Cities**: Discover new cities or reminisce past travels by searching cities.
- **Interactive GUI**: An intuitive interface built using SceneBuilder and JavaFX, making your journaling experience enjoyable.
- **Relational Database**: Reliable MySQL database at the backend to store user data and trip details.

## Project Structure

The application follows a structured MVC pattern with clear distinction between the GUI views, the controllers that manage logic, and the models that represent data.

## Getting Started

1. Ensure you have Java 11 or newer installed.
2. Clone the repository to your local machine.
3. Navigate to the project directory and run the application using Maven:
mvn clean install
mvn javafx:run
4. Once the application launches, you can register as a new user or login if you have an existing account.

## Dependencies

- JavaFX for the GUI
- MySQL Connector for database connectivity
- Other dependencies can be found in the `pom.xml` file.
