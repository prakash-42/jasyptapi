FROM openjdk:11-jre-slim-buster
RUN apt-get update && apt-get install curl -y
VOLUME /tmp
ARG BUILD_DIR
ADD $BUILD_DIR/jasyptapi*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
