FROM openjdk:8

EXPOSE 9090

ADD target/authorization-image.jar authorization-image.jar

ENTRYPOINT ["java", "-jar", "authorization-image.jar"]