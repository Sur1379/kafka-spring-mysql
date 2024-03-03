FROM maven:3.8.5-openjdk-17 as builder

ENV HOME=/usr/app

WORKDIR $HOME

COPY .mvn $HOME/.mvn
COPY mvnw $HOME/mvnw
COPY pom.xml $HOME/pom.xml
COPY ./src $HOME/src

RUN mvn clean install -DskipTests

FROM openjdk:17.0.2-slim

COPY --from=builder /usr/app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]