FROM eclipse-temurin:25-jdk-alpine AS build
<<<<<<< HEAD
WORKDIR /app
=======
WORKDIR /app/backend_api
>>>>>>> origin/main
COPY backend_api/ .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
<<<<<<< HEAD
COPY --from=build /app/target/*.jar SwapABookarooApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SwapABookarooApplication.jar"]
=======
COPY --from=build /app/backend_api/target/*.jar SwapABookarooApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SwapABookarooApplication.jar"]
>>>>>>> origin/main
