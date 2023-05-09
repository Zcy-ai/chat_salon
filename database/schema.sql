USE sr03p009;
DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id INTEGER PRIMARY KEY,
    lastName VARCHAR(50) NOT NULL ,
    firstName VARCHAR(50) NOT NULL ,
    login VARCHAR(50) NOT NULL ,
    password VARCHAR(50) NOT NULL,
    gender VARCHAR(6) NOT NULL ,
    admin INTEGER NOT NULL,
    CHECK ( admin = 0 OR admin = 1 ),
    CHECK ( gender = 'male' OR gender = 'female')
);
