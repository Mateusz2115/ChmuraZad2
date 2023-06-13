FROM openjdk:11 AS build
LABEL author="Mateusz Zajaczkowski"
WORKDIR /app
RUN javac Server.java
CMD ["java", "Server"]