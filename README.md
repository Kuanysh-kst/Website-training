Docker
    Commands:
        docker images: to see all images on machine

# Работа с базой данных

## [Задачи здесь](./tasks/database.md)

## Подготовка окружения

Мы используем [СУБД PostgreSQL](https://www.postgresql.org/)

Для того, чтобы не устанавливать локально всю базу данных, запускаем ее в Docker контейнере.
Для этого нужно сначала установить Docker

### Установка Docker на Linux машину

* Сначала добавим репозиторий
```shell
 sudo apt-get update
 sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
```
* Добавляем GPG ключ Docker
```shell
sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```
* Добавляем правильную конфигурацию Docker репозитория
```shell
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```
* Устанавливаем Docker
```shell
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
```
* Проверяем, что все работает
```shell
sudo docker run hello-world
```

### Установка Docker на Windows машину
* Скачиваем [установочник Docker](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe) и делаем
  по инструкции

### Установка клиента БД
Для удобного доступа к БД лучше всего использовать какой-нибудь GUI клиент. Хороший пример -
[DBeaver](https://dbeaver.io/download/), он бесплатный и подходит для большинства БД.

## Запуск Postgres
В корневой папке проекта лежит файл [docker-compose.yml](./docker-compose.yml), который является конфигурацией
для запуска Docker контейнеров.

Чтобы запустить контейнеры, указанные в файле, нужно выполнить команду
```shell
docker-compose up
```

## Остановка контейнера
Чтобы остановить контейнер с базой данных, достаточно выполнить команду
```shell
docker-compose down -v
```
из корневой папки проекта

## ./gradlew 
./gradlew clean test : удаляет build, запускает все тесты.

            