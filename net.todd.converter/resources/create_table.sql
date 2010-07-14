DROP TABLE IF EXISTS BIBLE;

CREATE TABLE BIBLE (
       id INT NOT NULL AUTO_INCREMENT,
       version VARCHAR(10) NOT NULL,
       book VARCHAR(30) NOT NULL,
       chapter INT NOT NULL,
       verse INT NOT NULL,
       text TEXT NOT NULL,
       order_id INT NOT NULL,
       PRIMARY KEY (id)
);
