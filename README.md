# API тестирование Яндекс.Самокат 🛴

![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.9%2B-orange.svg)
![JUnit](https://img.shields.io/badge/JUnit-4-green.svg)
![Allure](https://img.shields.io/badge/Allure-2.25-ff69b4.svg)
![Git](https://img.shields.io/badge/Git-2.50.1%2B-brightgreen)
![Coverage](https://img.shields.io/badge/Coverage-100%25-brightgreen)

Проект автоматизированного тестирования API сервиса [Яндекс.Самокат](http://qa-scooter.praktikum-services.ru/). Включает тесты основных и дополнительных эндпоинтов с использованием RestAssured и генерацией отчетов Allure.

[Документация API](http://qa-scooter.praktikum-services.ru/docs/)

## 🚀 Быстрый старт
### Требования
- **Java JDK** 11+ (рекомендуется Zulu 11.0.27+)
- **JUnit**  4.13.2
- **Maven** 3.9.0
- **Apache Maven** 3.9.0
- **Git** 2.50.1+ (для клонирования)
- **Allure Framework** 2.25.0
- **RestAssured** 5.4.0
- **Lombok** 1.18.30
- **JavaFaker** 1.0.2

### Установка и запуск
```bash
# Клонирование репозитория
git clone <repository-url>
cd Sprint_7
# Запуск тестов
mvn clean test
# Генерация отчета Allure
mvn allure:serve
```

## 📂 Структура проекта
```
Sprint_7/
├── src/test/java/ru/praktikum/
│   ├── clients/                     # Клиенты для API запросов
│   │   ├── BaseClient.java          # Базовый клиент с общими настройками
│   │   ├── CourierClient.java       # Клиент для работы с курьерами
│   │   └── OrderClient.java         # Клиент для работы с заказами
│   ├── constants/                   # Константы проекта
│   │   └── Endpoints.java           # Константы с путями API
│   ├── models/                      # Модели данных
│   │   ├── Courier.java             # Модель курьера (Lombok)
│   │   ├── CourierCredentials.java  # Модель учетных данных (Lombok)
│   │   └── Order.java               # Модель заказа (Lombok)
│   ├── tests/                       # Тестовые классы
│   │   ├── CourierCreationTest.java # Тесты создания курьера
│   │   ├── CourierLoginTest.java    # Тесты авторизации курьера
│   │   ├── CourierDeleteTest.java   # Тесты удаления курьера
│   │   ├── OrderCreationTest.java   # Тесты создания заказа (параметризованные)
│   │   ├── OrderListTest.java       # Тесты получения списка заказов
│   │   ├── OrderAcceptTest.java     # Тесты принятия заказа
│   │   └── OrderGetByTrackTest.java # Тесты получения заказа по треку
│   └── utils/
│       └── DataGenerator.java       # Генератор тестовых данных (JavaFaker)
├── pom.xml                          # Конфигурация Maven
├── .gitignore                       # Исключения для Git
├── LICENSE
└── README.md
```

## 📋 Функционал
### Основное задание ✅
- **Создание курьера**: Проверка успешного создания, дубликатов, обязательных полей
- **Логин курьера**: Авторизация, проверка ошибок, возврат ID
- **Создание заказа**: Параметризованные тесты с разными цветами (BLACK, GREY, оба, без цвета)
- **Список заказов**: Проверка возвращаемого списка заказов
- **Отчёт Allure**: Отчет доступен по ссылке, с шагами, запросами и ответами

### Дополнительное задание ✅
- **Удаление курьера**: Успешное удаление и обработка ошибок
- **Принятие заказа**: Работа с query-параметрами, проверка ошибок
- **Получение заказа по номеру**: Поиск по трек-номеру, обработка ошибок

## 📊 Покрытие тестами
**Результаты тестирования:**
- ✅ Всего тестов: 22
- ✅ Успешных: 22 (100%)
- ✅ Проваленных: 0
- ✅ Пропущенных: 0

**Протестированные эндпоинты:**
- `POST /api/v1/courier` - создание курьера
- `POST /api/v1/courier/login` - логин курьера
- `DELETE /api/v1/courier/{id}` - удаление курьера
- `POST /api/v1/orders` - создание заказа
- `GET /api/v1/orders` - список заказов
- `PUT /api/v1/orders/accept/{id}` - принятие заказа
- `GET /api/v1/orders/track` - заказ по номеру

## 💻 Пример работы
```java
// Создание курьера
Courier courier = new Courier("login", "password", "name");
Response response = courierClient.create(courier);
response.then().statusCode(SC_CREATED).body("ok", is(true));

// Логин курьера
Response loginResponse = courierClient.login(
        new CourierCredentials("login", "password"));
int courierId = loginResponse.path("id");
```
**Отчет Allure содержит:**
- Детальную информацию по каждому тесту с шагами выполнения
- HTTP запросы и ответы для каждого вызова API
- Время выполнения тестов и отдельных шагов
- Группировку тестов по сьютам и пакетам

## 📜 Лицензия
MIT License. Полный текст доступен в файле [LICENSE](https://github.com/Versaria/qa-mesto-selenium-test/blob/main/LICENSE).

## 🤝 Как внести вклад
1. Форкните проект
2. Создайте ветку для вашей функции (`git checkout -b feature/AmazingFeature`)
3. Закоммитьте изменения (`git commit -m 'Add some AmazingFeature'`)
4. Запушьте в ветку (`git push origin feature/AmazingFeature`)
5. Откройте Pull Request

---

<details>
<summary>🔧 Дополнительные команды</summary>

```bash
# Очистка и запуск тестов
mvn clean test

# Запуск тестов с генерацией отчета Allure
mvn clean test allure:report

# Просмотр отчета Allure
mvn allure:serve

# Очистка и запуск только определенных тестов
mvn clean test -Dtest=CourierCreationTest

# Генерация отчета Allure без запуска тестов
mvn allure:report

# Открытие отчета в браузере
allure open target/allure-report

# Проверка стиля кода
mvn checkstyle:check
```
</details>

**Примечание**: Проект создан для учебных целей и демонстрирует лучшие практики тестирования на Java.
