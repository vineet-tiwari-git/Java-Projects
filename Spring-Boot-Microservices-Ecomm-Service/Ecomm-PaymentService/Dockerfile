FROM openjdk:21-rc
ARG JAR_FILE=Spring-Boot-Microservices-Ecomm-Service/Ecomm-PaymentService/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
