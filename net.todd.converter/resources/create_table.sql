DROP TABLE IF EXISTS verse;

CREATE TABLE verse (
       id INT NOT NULL AUTO_INCREMENT,
       version VARCHAR(10) NOT NULL,
       book VARCHAR(30) NOT NULL,
       chapter INT NOT NULL,
       verse INT NOT NULL,
       text TEXT NOT NULL,
       orderId INT NOT NULL,
       PRIMARY KEY (id)
);
