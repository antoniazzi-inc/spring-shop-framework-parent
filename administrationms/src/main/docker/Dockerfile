FROM openjdk:15 as builder

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:15-slim-buster

VOLUME /tmp

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

ENV SPRING_PROFILE=prod

EXPOSE 80

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE}", "org.springframework.boot.loader.JarLauncher"]