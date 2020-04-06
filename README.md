### Project challenge DevOps


### Application Config

src/main/resources/env
    -prod.properties (production configs)
    -dev.properties (development configs)

### Maven build

Development - mvn clean install -Pdev

Production - mvn clean install -Pprod

java -jar target/challenge.jar

### Or Run With Docker

docker built -t app_name .

and Run

docker run -p 8080:8080 app_name


### circle.yml have configuration for deployment on heroku