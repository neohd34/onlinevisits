FROM openjdk:12-alpine

ARG ARTIFACT_NAME

ENV SPRING_PROFILES_ACTIVE docker

ADD https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-alpine-linux-amd64-v0.6.1.tar.gz dockerize.tar.gz
RUN tar xzf dockerize.tar.gz
RUN chmod +x dockerize
ADD ${ARTIFACT_NAME} /app.jar
RUN touch /app.jar

# HAZELCAST add-opens
RUN echo --add-modules=java.se, --add-exports=java.base/jdk.internal.ref=ALL-UNNAMED, --add-opens=java.base/java.lang=ALL-UNNAMED, --add-opens=java.base/java.nio=ALL-UNNAMED, --add-opens=java.base/sun.nio.ch=ALL-UNNAMED, --add-opens=java.management/sun.management=ALL-UNNAMED, --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED > java-add-opens

ENTRYPOINT ["java", "@java-add-opens", "--enable-preview", "-Xmx500m", "-Xms500m", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
