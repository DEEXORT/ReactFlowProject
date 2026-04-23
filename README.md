**ReactFlow** — это высокопроизводительная модульная платформа для управления контентом и пользовательскими реакциями. 
Проект реализует гибридную архитектуру хранения данных (Polyglot Persistence) и асинхронное взаимодействие через шину событий.

## 🏗 Архитектура проекта
Проект разделен на два ключевых модуля, взаимодействующих через Apache Kafka:
1. **Publisher Module (API Gateway & Core)**
  * **Стек**: Spring Boot, Spring Data JPA, PostgreSQL.
  * **Функция**: Предоставляет REST API (OpenAPI/Swagger) для внешних потребителей.
  * **Доступ**: Разделяет доступ на Open API (публичный) и Secure API (защищенный JWT).
  * **Интеграция**: Реализует паттерн Request-Reply для синхронного получения данных из Kafka.
2. **Discussion Module (Data Service)**
  * **Стек**: Spring Data Cassandra, Apache Cassandra.
  * **Функция**: Хранение и обработка большого объема пользовательских реакций.
  * **Миграции**: Автоматизированное управление схемой Cassandra через Liquibase.

<img width="1536" height="1024" alt="ReactFlow" src="https://github.com/user-attachments/assets/30080a3b-0b47-4fca-acb1-7cf0a82b7e35" />

## 🛠 Технологический стек
* **Java 21 & Spring Boot 3.5.9**
* **Database**:
  * **Apache Cassandra**: Масштабируемое хранилище для реакций.
  * **Redis**: Кеширование и оптимизация.
  * **PostgreSQL**: Хранение мастер-данных и метаданных контента.
* **Migration**: Liquibase.
* **Messaging**: Apache Kafka (асинхронный обмен событиями).
* **Documentation**: SpringDoc OpenAPI (Swagger).
* **Testing**: Testcontainers (PostgreSQL, Cassandra, Redis), JUnit 5, AssertJ

## 🚀 API Endpoints
Система предоставляет обширный интерфейс для работы с четырьмя основными сущностями: Writers, Topics, Markers и Reactions.
**Основные категории API:**
| Сущность | Open API (No Auth) | Secure API (JWT Required) | Описание |
| --- | --- | --- | --- |
| **Writer** | /api/v1/writers | /api/v2/writers | Управление авторами контента |
| **Topic** | /api/v1/topics | /api/v2/topics | Создание и получение топиков/обсуждений |
| **Marker** | /api/v1/markers | /api/v2/markers | Категоризация и тегирование |
| **Reaction** | /api/v1/reactions | /api/v2/reactions | Лайки, отзывы и пользовательская активность |

## 🔧 Запуск сервисов (Docker Compose)
1. Установить Docker Compose.
2. Клонировать репозиторий.
3. Запустить команду в командной строке:

  ```bash
  docker-compose up -d --build
  ```
4. Открыть [http://localhost:24110](https://www.google.com/search?q=http://localhost:24110)
