#
# Build stage
#
FROM maven:3.8.4-jdk-8-slim AS build
COPY ./src /home/bezkoder-app/src
COPY ./pom.xml /home/bezkoder-app
RUN mvn -f /home/bezkoder-app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:8-jre-slim
COPY --from=build /home/bezkoder-app/target/*.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
