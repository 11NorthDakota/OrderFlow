# OrderFlow

Микросервисная систему, в которой пользователи могут оформлять заказы на продукты. 
Система валидирует заказанные товары, проверяет наличие на складе, резервирует товар и отправляет пользователю уведомление о результате. 
Все взаимодействия между сервисами осуществляются через Kafka и REST.


##Поток обработки
Пользователь отправляет заказ в Order Service

Order Service проверяет наличие каждого product в Product Service

После валидации отправляется событие order.created в Kafka

Inventory Service проверяет наличие на складе и:
 -Отправляет inventory.reserved (если достаточно)
 -Отправляет inventory.failed (если недостаточно)

Notification Service получает событие и логирует/уведомляет пользователя