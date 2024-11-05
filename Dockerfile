# Etapa 1: Construcción de la aplicación
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias
COPY pom.xml .

# Descarga las dependencias (sin copiar todo el código aún)
RUN mvn dependency:go-offline

# Copia el código fuente de la aplicación
COPY src /app/src

# Compila y empaqueta la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:21-jdk AS runtime

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo .jar desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Expone el puerto en el que la aplicación escucha (por defecto Spring Boot usa el puerto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

