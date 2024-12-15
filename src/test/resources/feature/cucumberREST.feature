# language: ru
@All
@addingItemRest
Функция: Добавление товара через REST

  Предыстория:
    * создаем куки сессии "https://qualit.applineselenoid.fvds.ru/"

  @addingExoFruitRest
  Сценарий: Проверка добавления экзотического фрукта через REST
    * указываем параметры товара "Манго" "FRUIT" true
    * делаем POST запрос на добавление товара
    * считываем список товаров GET запросом
    * ищем нужный товар "Манго" "FRUIT" true
    * удаляем добавленные в api "https://qualit.applineselenoid.fvds.ru/" записи

  @addingBasicFruitRest
  Сценарий: Проверка добавления обычного фрукта через REST
    * указываем параметры товара "Груша" "FRUIT" false
    * делаем POST запрос на добавление товара
    * считываем список товаров GET запросом
    * ищем нужный товар "Груша" "FRUIT" false
    * удаляем добавленные в api "https://qualit.applineselenoid.fvds.ru/" записи

  @addingExoVegetableRest
  Сценарий: Проверка добавления экзотического овоща через REST
    * указываем параметры товара "Артишок" "VEGETABLE" true
    * делаем POST запрос на добавление товара
    * считываем список товаров GET запросом
    * ищем нужный товар "Артишок" "VEGETABLE" true
    * удаляем добавленные в api "https://qualit.applineselenoid.fvds.ru/" записи

  @addingBasicVegetableRest
  Сценарий: Проверка добавления обычного овоща через REST
    * указываем параметры товара "Кабачок" "VEGETABLE" false
    * делаем POST запрос на добавление товара
    * считываем список товаров GET запросом
    * ищем нужный товар "Кабачок" "VEGETABLE" false
    * удаляем добавленные в api "https://qualit.applineselenoid.fvds.ru/" записи