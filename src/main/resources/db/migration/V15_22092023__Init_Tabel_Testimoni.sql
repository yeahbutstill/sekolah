CREATE TABLE IF NOT EXISTS tbl_testimoni
(
    id          UUID           DEFAULT uuid_generate_v4() NOT NULL,
    nama        varchar(30)    DEFAULT NULL,
    isi         varchar(120)   DEFAULT NULL,
    email       varchar(35)    DEFAULT NULL,
    created_by  varchar(50),
    created_on  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by varchar(50),
    modified_on timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    status_record varchar(50) DEFAULT 'ACTIVE' NOT NULL,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

-- --------------------------------------------------------
