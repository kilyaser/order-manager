- запуск процесса сборки проекта 
```
mvn clean package
```
- Создание Docker-образа, включая все необходимые зависимости и конфигурации.
```
mvn spring-boot:build-image
```
- Создать и запустить все контейнеры
```
docker-compose up -d
```

- Для перезапуска только одного сервиса:
```
docker-compose restart order-manager-api
```