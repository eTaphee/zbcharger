FROM openjdk:17
EXPOSE 8080
RUN mkdir -p deploy
WORKDIR /deploy
COPY ./build/libs/zbcharger-0.0.1-SNAPSHOT.jar zbcharger.jar

ENTRYPOINT ["java", "-jar", "/deploy/zbcharger.jar"]
