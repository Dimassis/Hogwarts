CREATE TABLE Person (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        age INT CHECK (age >= 0),
                        has_license BOOLEAN NOT NULL DEFAULT FALSE,
                        car_id INT REFERENCES Car(id) -- Внешний ключ на машину
);

CREATE TABLE Car (
                     id SERIAL PRIMARY KEY,
                     brand VARCHAR(100) NOT NULL,
                     model VARCHAR(100) NOT NULL,
                     price DECIMAL(10, 2) NOT NULL
);
