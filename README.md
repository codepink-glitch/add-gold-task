
Запуск:
 ``docker-compose up``
  
Для прослушивания эндпоинтов использовал [spark](http://sparkjava.com), в качестве бд postgreSQL, для создания connection pool использовал apache dbcp2.

Логика добавления золота в клан от игрока сделана на основе двух synchronized блоков по первичным ключам клана и игрока, чтобы их золото во время передачи не менялось.
Проверено нагрузочным тестированием jmeter, целостность базы данных при 1000 запросов по передаче золота от 100 игроков сохранена.
  
 # Эндпоинты: 
  # /clans
  GET
  Получить информацию о клане по id.
  query param: id 
  Пример /clans?id=1
  
  POST 
  Создание нового клана
  в теле запроса json с одним параметром: название клана
  Пример:
  {
    "clanName": "new_fresh_clan"
  }
  
  PATCH
  Добавить/уменьшить золото клана.
  В теле запроса json с id клана и количеством золота (отрицательные значения разрешены).
  Пример: 
  {
    "clanId": 10,
    "goldAmount": 1000
  }
  
  
  # /players
  GET
  Получить информация о игроке по id.
  query param: id
  Пример /players?id=1
  
  POST
  Создание нового игрока
  Пример: 
  {
    username": "new_fresh_player"
  }
  
  PATCH
  Добавить/уменьшить золото игрока.
  В теле запроса json с id игрока и количеством золота (отрицательные значения разрешены).
  Пример: 
  {
    "playerId": 101,
    "goldAmount": -100
  }
  
  # /playerAddGold
  POST
  Передать золото от игрока в клан.
  В теле запроса json с id игрока, id клана и количеством золота.
  Пример:
  {
    "playerId": 1, 
    "clanId": 1,
    "goldAmount": 100
  }
  
  # /logs
  GET
  Без параметров
  Получить все логи
  
  
  # Нагрузочное тестирование 
  ![alt text](https://raw.githubusercontent.com/codepink-glitch/add-gold-task/main/jmeter-results/Graph%20Results.png)
  
  ![alt text](https://raw.githubusercontent.com/codepink-glitch/add-gold-task/main/jmeter-results/Summary%20Report.png)
  
