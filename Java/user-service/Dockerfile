# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR do projeto para o contêiner
COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar

# Expõe a porta em que o serviço será executado
EXPOSE 8085

# Comando para executar o serviço
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]
