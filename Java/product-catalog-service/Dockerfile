# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR do projeto para o contêiner
COPY target/product-catalog-service-0.0.1-SNAPSHOT.jar /app/product-catalog-service.jar

# Expõe a porta em que o serviço será executado
EXPOSE 8082

# Comando para executar o serviço
ENTRYPOINT ["java", "-jar", "/app/product-catalog-service.jar"]
