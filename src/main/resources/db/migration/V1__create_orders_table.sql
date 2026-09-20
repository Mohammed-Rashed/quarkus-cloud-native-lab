CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        product VARCHAR(255) NOT NULL,
                        quantity INTEGER NOT NULL,
                        status VARCHAR(50) NOT NULL
);