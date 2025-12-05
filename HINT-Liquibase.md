## Liquibase

# Проверить статус миграций
mvn liquibase:status
# Сгенерировать SQL для проверки (без выполнения)
mvn liquibase:updateSQL
# Выполнить миграции
mvn liquibase:update
# Проверить статус миграций
mvn liquibase:status
# Сгенерировать SQL для проверки (без выполнения)
mvn liquibase:updateSQL
# Выполнить миграции
mvn liquibase:update
# Откат на определенное количество изменений
mvn liquibase:rollback -Dliquibase.rollbackCount=1
# Откат к определенной дате
mvn liquibase:rollback -Dliquibase.rollbackDate=2024-01-01
# Создать diff между БД и changelog
mvn liquibase:diff
# Создать changelog из существующей БД
mvn liquibase:generateChangeLog

📋 Информационные команды (просмотр состояния)
1. mvn liquibase:status
   Что делает: Показывает, какие изменения из вашего changelog файла уже применены к базе данных, а какие еще нет.

Когда использовать:
✅ Перед деплоем - проверить, что все миграции применены
✅ При смене ветки в Git - понять, какие изменения из другой ветки уже в базе
✅ При проблемах - диагностика, почему что-то не работает
Пример вывода:
text
Changesets not yet applied:
- db/changelog/changes/001-create-auto-policies-table.sql::create_auto_policies_table::author
- db/changelog/changes/002-create-health-policies-table.sql::create_health_policies_table::author
  Преимущество: Видите разницу между кодом и базой данных БЕЗ внесения изменений.

2. mvn liquibase:updateSQL
   Что делает: Генерирует SQL-код, который будет выполнен, но НЕ выполняет его в базе.
Когда использовать:
✅ Для code review - отправить SQL коллеге на проверку
✅ Перед продакшеном - показать DBA, что будет выполнено
✅ Для отладки - увидеть точный SQL, который сгенерирует Liquibase
✅ Для документации - сохранить SQL скрипт для истории
Преимущество: Безопасность. Видите, что именно будет выполнено, перед тем как это сделать.

🚀 Команды выполнения миграций
3. mvn liquibase:update
   Что делает: Применяет все непримененные миграции к базе данных.
Когда использовать:
✅ Локальная разработка - после создания новой миграции
✅ CI/CD пайплайн - автоматическое обновление тестовой БД
✅ Деплой на staging/production - обновление схемы БД
✅ Когда коллега добавил новую миграцию - синхронизация

Что происходит внутри:
Liquibase читает ваш changelog-master.yaml
Проверяет таблицу databasechangelog (создаёт её, если нет)
Находит все неприменённые изменения (changesets)
Выполняет их в порядке, указанном в changelog
Записывает информацию о применённых changesets в databasechangelog
Важно: Эта команда идемпотентна - можно запускать много раз, она применит только новые изменения.

🔙 Команды отката (rollback)
4. mvn liquibase:rollback -Dliquibase.rollbackCount=1
   Что делает: Откатывает последнее применённое изменение.
Когда использовать:
✅ Ошибка в миграции - откатить проблемное изменение
✅ Тестирование миграций - проверить, что rollback работает
✅ Отмена последнего изменения - если что-то пошло не так
Пример: У вас было 10 миграций, запускаете команду - остаётся 9.

Ограничение: Для этой команды нужно, чтобы в changeset был указан rollback (в SQL формате вы должны были указать --rollback блок).

5. mvn liquibase:rollback -Dliquibase.rollbackDate=2024-01-01
   Что делает: Откатывает все изменения, применённые после указанной даты.
Когда использовать:
✅ Массовые проблемы - если несколько миграций вызвали проблемы
✅ Возврат к состоянию на определённую дату
✅ После неудачного релиза - откат к предыдущей версии

🛠 Утилитарные команды
6. mvn liquibase:diff
   Что делает: Сравнивает текущую схему БД с вашим changelog (или с другой БД) и показывает разницу.
Когда использовать:
✅ Поиск расхождений - если кто-то вручную изменил БД
✅ Сравнение сред - dev vs production
✅ Проверка целостности - убедиться, что всё соответствует
Пример использования:

bash
# Сравнить локальную БД с changelog
mvn liquibase:diff

# Сравнить две разные БД
mvn liquibase:diff \
-Dliquibase.referenceUrl=jdbc:postgresql://localhost:5432/prod_db \
-Dliquibase.referenceUsername=admin \
-Dliquibase.referencePassword=qwerty
7. mvn liquibase:generateChangeLog
   Что делает: Создаёт changelog из существующей базы данных.
Когда использовать:
✅ Подключение к legacy проекту - когда БД уже есть, а миграций нет
✅ Первоначальная настройка - создать базовый changelog
✅ Аварийное восстановление - если потеряли changelog файлы
Пример:
bash
# Создать changelog из существующей БД
mvn liquibase:generateChangeLog

# Результат: Liquibase создаст файл с текущей структурой БД
🎯 Практические сценарии использования
Сценарий 1: Локальная разработка новой фичи
bash
# 1. Создал новую таблицу в SQL файле
# 2. Хочу проверить, что SQL синтаксически правильный
mvn liquibase:updateSQL

# 3. SQL выглядит хорошо, применяю
mvn liquibase:update

# 4. Ой, что-то не так! Откатываю
mvn liquibase:rollback -Dliquibase.rollbackCount=1

# 5. Исправил ошибку, применяю снова
mvn liquibase:update
Сценарий 2: Подготовка к релизу
bash
# 1. Проверить, какие миграции будут применены
mvn liquibase:status

# 2. Сгенерировать SQL для проверки командой
mvn liquibase:updateSQL > migration.sql

# 3. Отправить migration.sql на review DBA

# 4. После approval применить
mvn liquibase:update
Сценарий 3: Проблема в production
bash
# 1. Новая миграция сломала прод
# 2. Быстро откатываемся
mvn liquibase:rollback -Dliquibase.rollbackCount=1

# 3. Ищем проблему, исправляем
# 4. Когда исправили, применяем заново
mvn liquibase:update

📊 Таблица: Когда какую команду использовать
Ситуация	Команда	Почему
Новый разработчик присоединился	mvn liquibase:update	Чтобы синхронизировать его локальную БД
Создал новую миграцию	mvn liquibase:updateSQL → mvn liquibase:update	Проверить, затем применить
Хочу посмотреть, что изменится	mvn liquibase:updateSQL	Безопасный просмотр SQL
Что-то сломалось после миграции	mvn liquibase:rollback	Быстрый откат
Подозреваю, что БД отличается	mvn liquibase:diff	Найти расхождения
Переношу старый проект на Liquibase	mvn liquibase:generateChangeLog	Создать начальный changelog
Перед деплоем на прод	mvn liquibase:status	Убедиться, что всё применено
⚠️ Важные предупреждения
1. Никогда не меняйте применённые changesets!
   Если миграция уже запущена на прод, НЕ ИЗМЕНЯЙТЕ её файл. Создайте новую миграцию, которая исправит проблему.

2. Всегда пишите rollback блоки
   В SQL файлах:

sql
--changeset author:add_new_column
ALTER TABLE auto_insurance_policies ADD COLUMN new_column VARCHAR(100);

--rollback
ALTER TABLE auto_insurance_policies DROP COLUMN new_column;
3. Тестируйте миграции на тестовой БД
   bash
# Для тестовой БД
mvn liquibase:update -Dliquibase.url=jdbc:postgresql://test-db

# Для прод БД
mvn liquibase:update -Dliquibase.url=jdbc:postgresql://prod-db
4. Используйте транзакции для PostgreSQL
   Liquibase по умолчанию использует транзакции для PostgreSQL, но некоторые операции (CREATE INDEX CONCURRENTLY) не могут выполняться в транзакции.

🚀 Рекомендованный workflow
bash
# 1. Локальная разработка
mvn liquibase:updateSQL  # проверяю SQL
mvn liquibase:update     # применяю

# 2. Делаю Pull Request
# В CI/CD пайплайне:
mvn liquibase:updateSQL  # проверка в CI
# Если нужно, артефакт для DBA

# 3. После мержа в main
# В деплой пайплайне:
mvn liquibase:update     # автоматическое применение

# 4. Если что-то пошло не так
mvn liquibase:rollback -Dliquibase.rollbackCount=1