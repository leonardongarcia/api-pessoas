FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY target/*.jar /app/api.jar

RUN apk update && apk add bash

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]