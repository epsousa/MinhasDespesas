FROM openjdk:8
ADD target/MinhasDespesas-0.0.1-SNAPSHOT.jar MinhasDespesas-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "MinhasDespesas-0.0.1-SNAPSHOT.jar"]