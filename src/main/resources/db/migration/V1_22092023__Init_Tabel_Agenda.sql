CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS tbl_agenda(
    id          UUID           DEFAULT uuid_generate_v4() NOT NULL,
    nama        varchar(200)   DEFAULT NULL,
    deskripsi   text,
    tempat      varchar(90)    DEFAULT NULL,
    waktu       varchar(30)    DEFAULT NULL,
    keterangan  varchar(200)   DEFAULT NULL,
    created_by  varchar(50),
    created_on  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by varchar(50),
    modified_on timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    status_record varchar(50) DEFAULT 'ACTIVE' NOT NULL,
    version BIGINT DEFAULT 0,
    PRIMARY KEY (id)
);

--
-- Dumping data untuk tabel tbl_agenda
--

INSERT INTO tbl_agenda (nama, deskripsi, tempat, waktu, keterangan, created_by)
VALUES ('Penyembelihan Hewan Kurban Idul Adha 2019',
        'Idul Adha yang biasa disebut lebaran haji atapun lebaran kurban sangat identik dengan penyembelihan hewan kurban. M-Sekolah tahun ini juga melakukan penyembelihan hewan kurban. Yang rencananya akan dihadiri oleh guru-guru, siswa dan pengurus OSIS.',
        'M-Sekolah', '08.00 - 11.00 WIB',
        'Dihadiri oleh guru-guru, siswa dan pengurus OSIS', 'Hendi Santika'),

       ('Peluncuran Website Resmi M-Sekolah',
        'Peluncuran website resmi  M-Sekolah, sebagai media informasi dan akademik online untuk pelayanan pendidikan yang lebih baik kepada siswa, orangtua, dan masyarakat pada umumnya semakin meningkat.',
        'M-Sekolah', '07.30 - 12.00 WIB', '-', 'Hendi Santika'),

       ('Penerimaan Raport Semester Ganjil Tahun Ajaran 2019-2018',
        'Berakhirnya semester ganjil tahun pelajaran 2016-2019, ditandai dengan pembagian laporan hasil belajar.',
        'M-Sekolah', '07.30 - 12.00 WIB',
        'Untuk kelas XI dan XII, pembagian raport dimulai pukul 07.30 WIB. Sedangkan untuk kelas X pada pukul 09.00 WIB. Raport diambil oleh orang tua/wali murid masing-masing.',
        'Hendi Santika');