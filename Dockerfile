FROM openjdk:17-jdk-alpine

WORKDIR /teammanager

COPY teammanager/target/teammanager-*.jar /teammanager/teammanager.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/teammanager/teammanager.jar"]
