В интерфейсе GBRepository добавлен метод delete, в слой UserController добавлен метод deleteUser. В классе UserRepositiry переопределен метод delete c реализацией удаления абонента по индексу.
Метод createUser из UserView я перенес в слой UserController сделав его публичным статическим и поменял  ссылки на которые ругалась IDEA.

