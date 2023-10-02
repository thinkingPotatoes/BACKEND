FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/potatoesProject-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} potatoesProject.jar
ENTRYPOINT ["docker cp","build/libs/keystore.jks","container:/"]
ENTRYPOINT ["java","-jar","/potatoesProject.jar"]