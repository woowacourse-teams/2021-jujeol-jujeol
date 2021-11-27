FROM openjdk:11-jdk

WORKDIR /home/spring

COPY build/libs/*.jar /jujeol/application.jar

EXPOSE 8080

CMD ["java", "-jar", "/jujeol/application.jar"]
