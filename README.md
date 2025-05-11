# FlickFinder API

The project focuses on the back-end development of a mobile movie search application called FlickFinder.

## 📚 Project Overview

FlickFinder is a movie search app that allows users to search for movies based on actors, directors, and ratings. The application uses a RESTful API built in Java, backed by a SQLite database of movies and people sourced from IMDB.

## Implementation and Testing
- Developed back-end functionality using Java and Javalin.
- Implemented and tested API routes for:
  - Retrieving people by ID and listing all people.
  - Retrieving stars of a movie and movies by a specific star.
  - Ordering movies by rating and limiting results.
- Used a Model-View-Controller (MVC) pattern.
- Created unit and integration tests for models, DAOs, and controllers.

## 🔧 Technologies Used
- Java 17
- Javalin (REST API framework)
- SQLite (database)
- JUnit 5 (unit testing)
- Mockito (mocking for controller tests)
- Git & GitLab

## 📁 Structure

src/
├── main/
│ ├── java/
│ │ └── com/flickfinder/
│ │ ├── controller/
│ │ ├── dao/
│ │ ├── model/
│ │ └── AppConfig.java
│ └── resources/
│ └── movies.db
└── test/
└── java/
└── com/flickfinder/
├── controller/
├── dao/
├── model/
└── IntegrationTests.java
