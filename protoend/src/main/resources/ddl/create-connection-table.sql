CREATE TABLE connection (
    id             INTEGER,
    auth           BLOB,
    auth_type      INTEGER,
    type           INTEGER,
    request_detail BLOB,
    url            VARCHAR (255),
    PRIMARY KEY (
        id
    )
);