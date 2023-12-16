create database zbcharger character set utf8mb4 collate utf8mb4_general_ci;

CREATE USER 'zbuser'@'localhost' IDENTIFIED BY 'zbpass';
CREATE USER 'zbuser'@'%' IDENTIFIED BY 'zbpass';

GRANT ALL PRIVILEGES ON zbcharger.* TO 'zbuser'@'localhost';
GRANT ALL PRIVILEGES ON zbcharger.* TO 'zbuser'@'%';

use zbcharger;

CREATE TABLE `member`
(
    `id`                BIGINT       NOT NULL,
    `password`          VARCHAR(60)  NOT NULL,
    `name`              VARCHAR(24)  NOT NULL,
    `phone`             VARCHAR(24)  NOT NULL,
    `email`             VARCHAR(480) NOT NULL,
    `email_verified_at` DATETIME,
    `created_at`        DATETIME     NOT NULL,
    `updated_at`        DATETIME     NULL
);

ALTER TABLE `member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (`id`);

ALTER TABLE `member`
    MODIFY COLUMN `id` BIGINT AUTO_INCREMENT NOT NULL;