FROM eclipse-temurin:21-jre-jammy
LABEL authors="fikalinichev"
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]