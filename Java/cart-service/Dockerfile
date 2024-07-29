# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo JAR do projeto para o contêiner
COPY target/cart-service-0.0.1-SNAPSHOT.jar /app/cart-service.jar

# Expõe a porta em que o serviço será executado
EXPOSE 8083

# Comando para executar o serviço
ENTRYPOINT ["java", "-jar", "/app/cart-service.jar"]
