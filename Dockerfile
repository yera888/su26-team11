FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app/backend_api
COPY backend_api/ .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/backend_api/target/*.jar SwapABookarooApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SwapABookarooApplication.jar"]