# language: ru
@all
@addingitemJDBC
Функция: Добавление товара БД

  Предыстория:
    * открыта страница по адресу "http://localhost:8080/"
    * открыта БД по адресу "jdbc:h2:tcp://localhost:9092/mem:testdb"
    * открыта вкладка Товары

@correct
Сценарий: Проверка добавления нового товара в БД
* в БД проверено "отсутствие" товара с названием "Артишок", типом "VEGETABLE", экзотичностью 1
* выполняется нажатие на элемент "кнопка Добавить"
* в поле 'Название' вводится текст "Артишок"
* в списке 'Тип' выбирается элелемент "Овощ"
* выполняется нажатие на элемент "чек-бокс Экзотический"
* выполняется нажатие на элемент "кнопка Сохранить"
* в БД добавлен новый товар c названием "Артишок", типом "VEGETABLE", экзотичностью 1
* удалена добавленная в БД запись

@correct
Сценарий: Проверка добавления существующего товара в БД
Если в БД проверено "отсутствие" товара с названием "Груша", типом "FRUIT", экзотичностью 0
То в БД добавлен товар с названием "Груша", типом "FRUIT", экзотичностью 0
* выполняется нажатие на элемент "кнопка Добавить"
* в поле 'Название' вводится текст "Груша"
* в списке 'Тип' выбирается элелемент "Фрукт"
* выполняется нажатие на элемент "кнопка Сохранить"
* в БД добавлен 2 товар с названием "Груша", типом "FRUIT", экзотичностью 0
* удалена добавленная в БД запись