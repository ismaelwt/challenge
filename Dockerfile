FROM openjdk:8-jre-alpine

ARG JAR_FILE=target/challenge.jar

ARG JAR_LIB_FILE=target/lib/

RUN mkdir -p /usr/local/app

COPY ${JAR_FILE} app.jar

ADD ${JAR_LIB_FILE} lib/

ENTRYPOINT ["java","-jar","-server","app.jar"]