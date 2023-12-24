create
database zbcharger character set utf8mb4 collate utf8mb4_general_ci;

CREATE
USER 'zbuser'@'localhost' IDENTIFIED BY 'zbpass';
CREATE
USER 'zbuser'@'%' IDENTIFIED BY 'zbpass';

GRANT ALL PRIVILEGES ON zbcharger.* TO
'zbuser'@'localhost';
GRANT ALL PRIVILEGES ON zbcharger.* TO
'zbuser'@'%';

use
zbcharger;

CREATE TABLE `member`
(
    `id`         BIGINT       NOT NULL,
    `password`   VARCHAR(60)  NOT NULL,
    `name`       VARCHAR(24)  NOT NULL,
    `phone`      VARCHAR(24)  NOT NULL,
    `email`      VARCHAR(480) NOT NULL,
    `created_at` DATETIME     NOT NULL,
    `updated_at` DATETIME NULL
);

ALTER TABLE `member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (`id`);

ALTER TABLE `member`
    MODIFY COLUMN `id` BIGINT AUTO_INCREMENT NOT NULL;

CREATE TABLE `email_verification`
(
    `id`           uuid     NOT NULL,
    `member_id`    bigint   NOT NULL COMMENT 'AI',
    `expired_at`   datetime NULL,
    `verified_yn`  tinyint(1) NOT NULL,
    `verified_at`  datetime NULL,
    `last_sent_at` datetime NULL,
    `send_count`   int      NOT NULL,
    `created_at`   datetime NOT NULL,
    `updated_at`   datetime NULL
);

ALTER TABLE `email_verification`
    ADD CONSTRAINT `PK_EMAIL_VERIFICATION` PRIMARY KEY (`id`);

ALTER TABLE `email_verification`
    ADD CONSTRAINT `FK_member_TO_email_verification_1` FOREIGN KEY (`member_id`)
        REFERENCES `member` (`id`)
        ON DELETE CASCADE;
