

DROP TABLE IF EXISTS player;

CREATE TABLE player (
    id NUMBER(8) not null,
    player_name varchar(20) not null,

    created_dt DATE,
    created_by NUMBER(8),
    updated_dt Date,
    updated_by NUMBER(8),

    CONSTRAINT PLAYER_PK PRIMARY KEY (id)
);

