FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/potatoesProject-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} potatoesProject.jar
ENTRYPOINT ["java","-jar","/potatoesProject.jar"]