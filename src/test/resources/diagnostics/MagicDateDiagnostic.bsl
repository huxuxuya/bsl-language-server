День = Дата("00020101");
День = Дата("00020101") + Шаг; // ошибка
День = Дата("00020101121314") + Шаг; // ошибка
День = '00010102' + Шаг; // ошибка
Пока Сейчас < '12340101' Цикл // ошибка
    Сейчас = Сейчас + Шаг;
КонецЦикла;
Конец = '12340101';
Пока Сейчас < Конец Цикл
    Сейчас = Сейчас + Шаг;
КонецЦикла;
День = Дата("00010101") + Шаг; // исключение
День = '0001-01why not?01' + Шаг; // исключение
День = '0001-01why not?02' + Шаг; // ошибка