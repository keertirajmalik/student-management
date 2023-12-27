# Student Management Project

[![CodeQL](https://github.com/keertirajmalik/student-management/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/keertirajmalik/student-management/actions/workflows/codeql-analysis.yml)

I am creating this project to learn about the Spring. This project is a simple student management system. It will have
the following features:

### Feature to be implemented:

- [x] student information
- [ ] Exams info
- [ ] Homework assignment
- [ ] Adding UI to the project

## Technology Used:

    Spring
    Hibernate
    MySQL
    Liquibase

## Development Setup

### Mysql DB Setup

Create docker network using command:

```bash
docker network create my-mysqldb
```

Start the docker container using command:

```bash
docker run -d --net my-mysqldb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=student_data --name mysqldb mysql:latest --port 3306
```

Add IP to the hosts file:

```bash
sudo vi /etc/hosts
```

and inside the file add the following line at the end

```bash
127.0.0.1 mysqldb
```

Use this line as DB url for the project in `application.yaml` file

```yaml
spring.datasource.url: jdbc:mysql://mysqldb:3306/student_data?useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=TRUE
```

clone the project

```bash
git clone https://github.com/keertirajmalik/student-management.git
```

Open the project in your favourite IDE and run the following command to start the application

```bash
mvn spring-boot:run
```

Run the test cases using the following command

```bash
mvn test
```

### IntelliJ Setup

Install the [lombok](https://plugins.jetbrains.com/plugin/6317-lombok-plugin) plugin.

```
File(IntelliJ on Mac) → Preferences → Plugins → Marketplace → [Search for]"Lombok"
```

Install the [sonarlint](https://plugins.jetbrains.com/plugin/7973-sonarlintz) plugin.

``` 
File(IntelliJ on Mac) → Preferences → Plugins → Marketplace → [Search for]"SonarLint"
```

## API Documentation

API documentation is available at [Swagger](http://localhost:9090/swagger-ui.html) once the application is up and
running.
