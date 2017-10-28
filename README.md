# MyCalories

##
<br>


## Prerequisites

  - [Node.js with NPM](https://nodejs.org/en/download/) to run front-end side
  - Some IDE to run back-end side (recommending to use IntelliJ IDEA)
  - PostgreSQL Database
  - [Angular CLI](https://cli.angular.io/)

## Build and Run
First of all you need to configure the database and liquibase properties. Properties are located in `./src/main/resources/application.properties` file and in <br> `./src/main/resources/liquibase/liquibase.properties`' file.

> By default application is using PostgreSQL database(name: `MyCalories`, user: `postgres`, password: `admin`).

Run server in your IDE
> By default server will be running on port 8080.

## Development
- Go to `./frontend` folder and type `npm start` or `ng serve` to start front-end server for development.
> By default server will be running on port 4200.
