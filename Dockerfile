FROM openjdk:11 AS build
LABEL author="Mateusz Zajaczkowski"
WORKDIR /app
COPY src/Server.java .
RUN javac Server.java
CMD ["java", "Server"]