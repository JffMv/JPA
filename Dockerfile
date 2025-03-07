# Usa una imagen ligera de OpenJDK 17
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo en el contenedor
WORKDIR /usrapp

# Copia el JAR generado al contenedor
COPY target/web_side_CRUD-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-jar", "app.jar"]
