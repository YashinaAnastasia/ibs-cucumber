# language: ru
@All
@addingItem
Функция: Добавление товара

  Предыстория:
    * открыта страница по адресу "https://qualit.applineselenoid.fvds.ru/"
    * открыта вкладка Товары

  @addingExoFruit
  Сценарий: Проверка добавления экзотического фрукта
    * выполняется нажатие на элемент "кнопка Добавить"
    * в поле 'Название' вводится текст "Манго"
    * в списке 'Тип' выбирается элелемент "Фрукт"
    * выполняется нажатие на элемент "чек-бокс Экзотический"
    * выполняется нажатие на элемент "кнопка Сохранить"
    * в таблицу добавлен товар с названием "Манго", типом "Фрукт", экзотичностью "true"
    * удалена добавленная запись

  @addingBasicFruit
  Сценарий: Проверка добавления обычного фрукта
    * выполняется нажатие на элемент "кнопка Добавить"
    * в поле 'Название' вводится текст "Груша"
    * в списке 'Тип' выбирается элелемент "Фрукт"
    * выполняется нажатие на элемент "кнопка Сохранить"
    * в таблицу добавлен товар с названием "Груша", типом "Фрукт", экзотичностью "false"
    * удалена добавленная запись

  @addingExoVegetable
  Сценарий: Проверка добавления экзотического овоща
    * выполняется нажатие на элемент "кнопка Добавить"
    * в поле 'Название' вводится текст "Артишок"
    * в списке 'Тип' выбирается элелемент "Овощ"
    * выполняется нажатие на элемент "чек-бокс Экзотический"
    * выполняется нажатие на элемент "кнопка Сохранить"
    * в таблицу добавлен товар с названием "Артишок", типом "Овощ", экзотичностью "true"
    * удалена добавленная запись

  @addingBasicVegetable
  Сценарий: Проверка добавления обычного овоща
    * выполняется нажатие на элемент "кнопка Добавить"
    * в поле 'Название' вводится текст "Кабачок"
    * в списке 'Тип' выбирается элелемент "Овощ"
    * выполняется нажатие на элемент "кнопка Сохранить"
    * в таблицу добавлен товар с названием "Кабачок", типом "Овощ", экзотичностью "false"
    * удалена добавленная запись

