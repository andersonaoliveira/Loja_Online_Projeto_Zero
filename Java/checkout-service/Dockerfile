# Use uma imagem base do OpenJDK 17
FROM eclipse-temurin:17-jre-alpine

# Adicione um argumento para o JAR file
ARG JAR_FILE=target/*.jar

# Copie o JAR file para o container
COPY ${JAR_FILE} app.jar

# Exponha a porta em que a aplicação estará rodando
EXPOSE 8084

# Execute o JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
