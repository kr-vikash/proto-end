CREATE TABLE connection (
    id             INTEGER,
    type           INTEGER,
    request_detail BLOB,
    url            VARCHAR (255),
    PRIMARY KEY (
        id
    )
);