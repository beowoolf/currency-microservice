# Etap 1: Budowanie aplikacji
FROM openjdk:11-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etap 2: Budowanie obrazu
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.war app.war

# Uruchom aplikację Spring Boot
CMD ["java", "-jar", "app.war"]
