# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:21-jdk
WORKDIR /app

# O Maven gerou 'app.jar' por causa do <finalName> no pom.xml
COPY --from=build /app/target/app.jar app.jar

EXPOSE 8080

# Usamos o formato shell para que variáveis de ambiente sejam expandidas se necessário,
# mas o Spring Boot já reconhece SPRING_PROFILES_ACTIVE automaticamente.
# Altere a última linha para:
ENTRYPOINT ["java", "-Dspring.profiles.active=prd", "-jar", "app.jar"]