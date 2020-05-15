FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir /data
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.jar"]