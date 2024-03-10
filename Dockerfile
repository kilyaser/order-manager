# Используем официальный образ Maven для сборки проекта
FROM maven:3.8.4-openjdk-17-slim AS build

# Копируем исходный код проекта в Docker образ
COPY ./ /app
WORKDIR /app

# Собираем проект с помощью Maven
RUN mvn clean package

# Создаем образ OpenJDK для запуска приложения
FROM adoptopenjdk/openjdk17:alpine-jre

# Копируем JAR файл из сборочного образа в образ для запуска
COPY --from=build /app/target/*.jar /app/app.jar

# Запускаем приложение при старте контейнера
CMD ["java", "-jar", "/app/app.jar"]