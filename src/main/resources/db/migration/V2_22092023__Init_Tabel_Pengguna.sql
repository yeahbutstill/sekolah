CREATE TABLE IF NOT EXISTS tbl_pengguna
(
    id           UUID           DEFAULT uuid_generate_v4() NOT NULL,
    fullname     varchar(50)    DEFAULT NULL,
    moto         varchar(100)   DEFAULT NULL,
    jenkel       varchar(2)     DEFAULT NULL,
    username     varchar(30)    DEFAULT NULL,
    password     varchar(75)    DEFAULT NULL,
    tentang      text,
    email        varchar(50)    DEFAULT NULL,
    nohp         varchar(20)    DEFAULT NULL,
    facebook     varchar(50)    DEFAULT NULL,
    twitter      varchar(50)    DEFAULT NULL,
    linkedin     varchar(50)    DEFAULT NULL,
    photo        varchar(40)    DEFAULT NULL,
    photo_base64 text,
    filename     varchar(40)    DEFAULT NULL,
    file_content bytea          DEFAULT NULL,
    created_by   varchar(50),
    created_on   timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by  varchar(50),
    modified_on  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    status_record varchar(50) DEFAULT 'ACTIVE' NOT NULL,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (email, username)
);

CREATE TABLE IF NOT EXISTS role
(
    id          UUID           DEFAULT uuid_generate_v4() NOT NULL,
    role varchar(25),
    created_by   varchar(50),
    created_on   timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by  varchar(50),
    modified_on  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    status_record varchar(50) DEFAULT 'ACTIVE' NOT NULL,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE tbl_pengguna_roles
(
    user_id     UUID           NOT NULL,
    role_id     UUID           NOT NULL
);

ALTER TABLE tbl_pengguna_roles
    ADD CONSTRAINT FKrhfovtciq1l558cw6udg0h0d3 FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE tbl_pengguna_roles
    ADD CONSTRAINT FK55itppkw3i07do3h7qoclqd4k FOREIGN KEY (user_id) REFERENCES tbl_pengguna (id);

--
-- Dumping data untuk tabel tbl_pengguna
--


INSERT INTO tbl_pengguna (id, fullname, moto, jenkel, username, password, tentang, email, nohp, facebook, twitter,
                          linkedin)
VALUES ('f623b4b2-6d4a-4b5b-87ac-f62f2b2b51e0', 'Uzumaki Naruto', 'Just do it', 'L', 'user', '$2a$06$KdPmlUQcRaOM1177wRdgCuqMsaUE0pff7nmTV.exVLIyIKL7GcBmO',
        'I am a mountainner. to me mountainerring is a life', 'uzumaki_naruto@konohgakure.co.jp', '081277159401',
        'https://www.facebook.com/narutoofficialsns', 'twitter.com/naruto', 'https://www.linkedin.com/in/dani-setiawan-duke/');

INSERT INTO tbl_pengguna (id, fullname, moto, jenkel, username, password, tentang, email, nohp, facebook, twitter,
                          linkedin)
VALUES ('8bf1f3af-36ce-4f5a-a708-c6dd0c1b1354', 'Ismail Fajar', 'Just do it', 'L', 'fajar', '$2a$06$KdPmlUQcRaOM1177wRdgCuqMsaUE0pff7nmTV.exVLIyIKL7GcBmO',
        'I am a mountainner. to me mountainerring is a life', 'hendisantika@yahoo.co.id', '081277159401',
        'https://www.facebook.com/hendisantika/', 'twitter.com/hendisantika34', '');
INSERT INTO tbl_pengguna (id, fullname, moto, jenkel, username, password, tentang, email, nohp, facebook, twitter,
                          linkedin)
VALUES ('12818ac0-74a7-48b1-b11e-fd21a6b11482', 'Uchiha Madara', NULL, 'L', 'madara', '$2a$10$0uzYmIaRssNuKpOwbeAw8eENlxanNRSk31j9LH8jiCDl7rCCTb0cm', NULL,
        'uchiha_madara@konohagakure.com', '+6281311411511', NULL, NULL, NULL);
insert into tbl_pengguna (id, fullname, moto, jenkel, username, password, tentang, email, nohp, facebook, twitter,
                          linkedin)
VALUES ('5fb19496-c860-4e66-8ff1-41c8c8686c78', 'Hendi Santika', 'Just do it', 'L', 'admin', '$2a$06$KdPmlUQcRaOM1177wRdgCuqMsaUE0pff7nmTV.exVLIyIKL7GcBmO',
        'I am a mountainner. to me mountainerring is a life', 'hendisantika@yahoo.co.id', '081277159401',
        'https://www.facebook.com/hendisantika/', 'twitter.com/hendisantika34', '');


-- Dumping data untuk tabel tbl_role
--
INSERT INTO role (id, role)
VALUES ('e2766382-ca08-4e46-b45a-eb174ed16c0d', 'ADMIN'),
       ('2844f3dd-9202-4842-b9d3-08f4f2944889', 'USER');

-- --------------------------------------------------------
-- Dumping data untuk tabel tbl_user_role
--
INSERT INTO tbl_pengguna_roles (user_id, role_id)
VALUES ('5fb19496-c860-4e66-8ff1-41c8c8686c78', 'e2766382-ca08-4e46-b45a-eb174ed16c0d'),
       ('8bf1f3af-36ce-4f5a-a708-c6dd0c1b1354', 'e2766382-ca08-4e46-b45a-eb174ed16c0d'),
       ('f623b4b2-6d4a-4b5b-87ac-f62f2b2b51e0', '2844f3dd-9202-4842-b9d3-08f4f2944889');

-- --------------------------------------------------------