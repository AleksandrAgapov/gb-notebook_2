В интерфейсе GBRepository добавлен метод delete, в слой UserController добавлен метод deleteUser. В классе UserRepositiry переопределен метод delete c реализацией удаления абонента по индексу.
Метод createUser из UserView я перенес в слой UserController сделав его публичным статическим и поменял  ссылки на которые ругалась IDEA.
Мтоды readAll и метод saveAll перенес в слой UserRepository зделав замену File(DB_PATH) в методах. Немного изменил класс Main. Класс FileOperation и интерфейс Operation удалил вместе с пакетом dao.   

