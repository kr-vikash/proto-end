FROM openjdk:8-jre-slim

WORKDIR /app/proto-end


COPY protoend/target/protoend-1.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "protoend-1.0-SNAPSHOT.jar" ]