FROM openjdk:8-jre-alpine

ENV NEW_RELIC_APP_NAME="My Application in Docker"
ENV NEW_RELIC_LICENSE_KEY="60895537352f6ac8a8c171fd7525ea0cd59eNRAL"

ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.app_name='MY_APP_NAME'"
ENV JAVA_OPTS="$JAVA_OPTS -Dnewrelic.config.license_key='MY_LICENSE_KEY'"

ARG JAR_FILE=target/challenge.jar

ARG JAR_LIB_FILE=target/lib/

RUN mkdir -p /usr/local/app

COPY ${JAR_FILE} app.jar

ADD ${JAR_LIB_FILE} lib/

RUN unzip lib/newrelic-java-5.11.0.zip

RUN mkdir -p /newrelic/logs

CMD java $JAVA_OPTS -javaagent:newrelic/newrelic.jar -jar app.jar $PORT
