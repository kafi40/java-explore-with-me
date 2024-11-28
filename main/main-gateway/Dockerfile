FROM eclipse-temurin:21-jre-jammy
LABEL authors="fikalinichev"
VOLUME /temp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]