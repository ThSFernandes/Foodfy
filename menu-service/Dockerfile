# Use a imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Adicione o JAR da sua aplicação ao contêiner
COPY target/menu-service.jar /app/menu-service.jar

# Defina o diretório de trabalho
WORKDIR /app

# Exponha a porta em que a aplicação vai rodar
EXPOSE 8484

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "menu-service.jar"]
