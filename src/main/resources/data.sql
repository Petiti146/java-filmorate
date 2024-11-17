-- Вставка данных в таблицу MPAs
INSERT INTO mpas (name) VALUES ('G');
INSERT INTO mpas (name) VALUES ('PG');
INSERT INTO mpas (name) VALUES ('PG-13');
INSERT INTO mpas (name) VALUES ('R');
INSERT INTO mpas (name) VALUES ('NC-17');

-- Вставка данных в таблицу жанров
INSERT INTO genres (name) VALUES ('Комедия');
INSERT INTO genres (name) VALUES ('Драма');
INSERT INTO genres (name) VALUES ('Мультфильм');
INSERT INTO genres (name) VALUES ('Триллер');
INSERT INTO genres (name) VALUES ('Документальный');
INSERT INTO genres (name) VALUES ('Боевик');

-- Вставка данных в таблицу пользователей
INSERT INTO users (name, login, birthday, email) VALUES ('Иван Иванов', 'ivan', '1990-01-01', 'ivan@example.com');
INSERT INTO users (name, login, birthday, email) VALUES ('Петр Петров', 'petr', '1985-05-15', 'petr@example.com');
INSERT INTO users (name, login, birthday, email) VALUES ('Анна Сидорова', 'anna', '1995-11-22', 'anna@example.com');

-- Вставка данных в таблицу фильмов
INSERT INTO films (name, release_date, description, duration, rate) VALUES ('Фильм 1', '2020-01-01', 'Описание фильма 1', 120, 5);
INSERT INTO films (name, release_date, description, duration, rate) VALUES ('Фильм 2', '2019-05-15', 'Описание фильма 2', 90, 4);
INSERT INTO films (name, release_date, description, duration, rate) VALUES ('Фильм 3', '2018-11-22', 'Описание фильма 3', 150, 3);