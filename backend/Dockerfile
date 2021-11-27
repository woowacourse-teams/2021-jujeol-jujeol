FROM openjdk:11-jdk

WORKDIR /home/jujeol

COPY build/libs/*.jar /home/jujeol/application.jar

EXPOSE 8080

CMD ["java", "-jar", "/home/jujeol/application.jar"]
