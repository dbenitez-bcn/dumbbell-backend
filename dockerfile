FROM openjdk:11.0.3-jdk-stretch
RUN mkdir /usr/src/dumbbell
WORKDIR /usr/src/dumbbell
COPY . .
RUN ./gradlew build
EXPOSE 8080
CMD ["java", "-jar", "./build/libs/backend-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]
