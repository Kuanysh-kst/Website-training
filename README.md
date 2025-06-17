## ./gradlew 
./gradlew clean test : удаляет build, запускает все тесты.

## Для управления базы из терминала с помощью bash
psql -U admin -d webtest -h localhost -p 5432

## чтобы посмотреть таблицы
\dt 

##  очистить
\! clear

## команды для отображения таблиц
select * from users;
select id, email,  verification_code, verification_expiration, enabled from users;

select id, email,  verification_code, enabled from users; WITH deleted_user AS (SELECT id FROM users WHERE email = '@gmail.com')
DELETE FROM token WHERE user_id IN (SELECT id FROM deleted_user); DELETE FROM users WHERE email = '@gmail.com';

docker exec -it d6caf4ccd84a bash
psql -U admin -d webapp