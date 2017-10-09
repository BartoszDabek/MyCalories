CREATE TABLE IF NOT EXISTS category (
  id                BIGSERIAL PRIMARY KEY UNIQUE,
  name              VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product (
  id                BIGSERIAL PRIMARY KEY UNIQUE,
  category_id       BIGINT REFERENCES category(id),
  name              VARCHAR(255) NOT NULL,
  calories          INTEGER NOT NULL,
  proteins          INTEGER NOT NULL,
  fats              INTEGER NOT NULL,
  carbs             INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
  id                BIGSERIAL PRIMARY KEY UNIQUE,
  role_name         VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL      PRIMARY KEY UNIQUE,
  password          VARCHAR(255) NOT NULL,
  username          VARCHAR(255) NOT NULL,
  email             VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id           BIGINT NOT NULL REFERENCES users(id),
  role_id           BIGINT NOT NULL REFERENCES role(id),
  CONSTRAINT        user_role_pkey PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS daily_calories (
  id                BIGSERIAL PRIMARY KEY UNIQUE,
  user_id           BIGINT REFERENCES users(id),
  date              DATE,
  calories          INTEGER NOT NULL,
  proteins          INTEGER NOT NULL,
  fats              INTEGER NOT NULL,
  carbs             INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS meal (
  id                BIGSERIAL PRIMARY KEY UNIQUE,
  daily_calories_id BIGINT REFERENCES daily_calories(id),
  name              VARCHAR(255) NOT NULL,
  calories          INTEGER NOT NULL,
  proteins          INTEGER NOT NULL,
  fats              INTEGER NOT NULL,
  carbs             INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS product_meal (
  id                BIGSERIAL PRIMARY KEY UNIQUE,
  product_id        BIGINT REFERENCES product(id),
  meal_id           BIGINT REFERENCES meal(id),
  amount            INTEGER NOT NULL
);


INSERT INTO users (username, password, email) VALUES ('admin', 'admin', 'qwe@wp.pl');
INSERT INTO users (username, password, email) VALUES ('user', 'user', 'oho@gmail.com');

INSERT INTO role (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO role (role_name) VALUES ('ROLE_USER');

INSERT INTO users_roles (role_id, user_id) VALUES ('1', '1');
INSERT INTO users_roles (role_id, user_id) VALUES ('2', '1');
INSERT INTO users_roles (role_id, user_id) VALUES ('2', '2');

INSERT INTO category (name) VALUES ('spoyzwcye');
INSERT INTO category (name) VALUES ('mleczne');

INSERT INTO product(name, calories, proteins, fats, carbs, category_id) VALUES ('Tosty', 52, 8, 1, 30, 1);
INSERT INTO product(name, calories, proteins, fats, carbs, category_id) VALUES ('Ser', 60, 11, 12, 4, 2);

INSERT INTO daily_calories(date, calories, proteins, fats, carbs) VALUES (CURRENT_DATE, 1000, 100, 100, 100);
INSERT INTO daily_calories(date, calories, proteins, fats, carbs) VALUES (CURRENT_DATE, 3000, 300, 300, 300);

INSERT INTO meal(name, daily_calories_id, calories, proteins, fats, carbs) VALUES ('Sniadanie', 1, 400, 30, 10, 90);
INSERT INTO meal(name, daily_calories_id, calories, proteins, fats, carbs) VALUES ('Kolacja', 1, 500, 30, 10, 90);

INSERT INTO meal(name, daily_calories_id, calories, proteins, fats, carbs) VALUES ('Kolacja2', 2, 500, 30, 10, 90);
INSERT INTO meal(name, daily_calories_id, calories, proteins, fats, carbs) VALUES ('Kolacja23', 2, 500, 30, 10, 90);

INSERT INTO product_meal(amount, product_id, meal_id) VALUES (40, 1, 1);
INSERT INTO product_meal(amount, product_id, meal_id) VALUES (20, 2, 1);

INSERT INTO product_meal(amount, product_id, meal_id) VALUES (20, 1, 3);
INSERT INTO product_meal(amount, product_id, meal_id) VALUES (20, 2, 4);

