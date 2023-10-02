FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/potatoesProject-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} potatoesProject.jar
ARG KEYSTORE_FILE=build/libs/keystore.jks
ADD ${KEYSTORE_FILE} keystore.jks
ENTRYPOINT ["java","-jar","/potatoesProject.jar"]