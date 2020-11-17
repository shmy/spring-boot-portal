FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
ARG JAR_FILE=build/libs/portal-0.0.1-SNAPSHOT.jar
ARG CONF_FILE=src/main/resources/application-prod.yaml
COPY ${JAR_FILE} app.jar
COPY ${CONF_FILE} application-prod.yaml
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "--spring.profiles.active=prod"]