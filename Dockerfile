FROM openjdk-17
WORKDIR /app
COPY target/Food-Deliverys-0.0.1-SNAPSHOT.jar /app/Food-Deliverys-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app/target/Food-Deliverys-0.0.1-SNAPSHOT.jar"]