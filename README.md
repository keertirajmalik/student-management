# Student Management Project
[![Maven Package](https://github.com/keertirajmalik/student-management/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/keertirajmalik/student-management/actions/workflows/maven-publish.yml) [![CodeQL](https://github.com/keertirajmalik/student-management/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/keertirajmalik/student-management/actions/workflows/codeql-analysis.yml)

I am creating this web app to learn about the Spring. Where I plan to create a system where Students, Teachers details are stored. Those details are exposed using the API end points.

## Developement Setup

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
sudo vi /etc/hosts ,     and inside the file add the following line at the end  127.0.0.1 mysqldb
```

Use this line as DB url for the project in `application.yaml` file
```yaml
spring.datasource.url: jdbc:mysql://mysqldb:3306/student_data?useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=TRUE
```

### Sonar Setup
Install the [sonarlint](https://plugins.jetbrains.com/plugin/7973-sonarlintz) plugin.
``` 
File(IntelliJ on Mac) → Preferences → Plugins → Marketplace → [Search for]"SonarLint"
```


### Technology Used:
    Spring
    Hibernate
    JSP
    MySQL
    Liquibase

### Feature to be implemented:
   - [x] student information 
   - [ ] Exams info
   - [ ] Homework assignment
