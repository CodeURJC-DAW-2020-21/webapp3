FROM openjdk:11-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8443
WORKDIR /usr/app
CMD ["java","-jar","/app.jar"]

HEALTHCHECK CMD curl --fail http://localhost:8443 || exit 1