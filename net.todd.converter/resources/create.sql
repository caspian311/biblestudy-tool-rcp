DROP TABLE IF EXISTS BIBLE;

CREATE TABLE BIBLE (
	BIB_ID INT NOT NULL AUTO_INCREMENT,
	BIB_VERSION VARCHAR(10) NOT NULL,
	BIB_BOOK VARCHAR(30) NOT NULL,
	BIB_CHAPTER INT NOT NULL,
	BIB_VERSE INT NOT NULL,
	BIB_TEXT TEXT NOT NULL,
	PRIMARY KEY (BIB_ID)
);
