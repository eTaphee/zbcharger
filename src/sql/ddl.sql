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
    `id`                BIGINT       NOT NULL,
    `password`          VARCHAR(60)  NOT NULL,
    `name`              VARCHAR(24)  NOT NULL,
    `phone`             VARCHAR(24)  NOT NULL,
    `email`             VARCHAR(480) NOT NULL,
    `email_verified_at` DATETIME,
    `created_at`        DATETIME     NOT NULL,
    `updated_at`        DATETIME NULL
);

ALTER TABLE `member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (`id`);

ALTER TABLE `member`
    MODIFY COLUMN `id` BIGINT AUTO_INCREMENT NOT NULL;

CREATE TABLE `email_verification`
(
    `id`          uuid     NOT NULL,
    `member_id`   bigint   NOT NULL COMMENT 'AI',
    `expired_at`  datetime NOT NULL,
    `verified_yn` tinyint(1) NOT NULL,
    `created_at`  datetime NOT NULL,
    `updated_at`  datetime NULL
);

ALTER TABLE `email_verification`
    ADD CONSTRAINT `PK_EMAIL_VERIFICATION` PRIMARY KEY (`id`);

ALTER TABLE `email_verification`
    ADD CONSTRAINT `FK_member_TO_email_verification_1` FOREIGN KEY (`member_id`)
        REFERENCES `member` (`id`)
        ON DELETE CASCADE;

CREATE TABLE `station`
(
    `id`                       varchar(8)   NOT NULL,
    `company_id`               char(2)      NOT NULL COMMENT '기관 아이디',
    `name`                     varchar(100) NOT NULL,
    `address`                  varchar(150) NOT NULL,
    `use_time`                 varchar(50)  NOT NULL,
    `area_code`                char(2)      NOT NULL COMMENT '시도 코드(행정구역코드 앞 2자리)',
    `area_detail_code`         char(5) NULL COMMENT '지역구분 상세(행정구역코드 앞 2자리 + 법정동코드3자리)',
    `station_kind_code`        char(2) NULL,
    `station_kind_detail_code` char(4) NULL,
    `parking_free_yn`          tinyint(1) NULL COMMENT 'Y/N/NULL',
    `note`                     varchar(200) NULL,
    `use_limit_yn`             tinyint(1) NOT NULL,
    `use_limit_detail`         varchar(100) NULL,
    `traffic_yn`               tinyint(1) NOT NULL,
    `lat` double NOT NULL,
    `lng` double NOT NULL,
    `created_at`               datetime     NOT NULL,
    `updated_at`               datetime NULL
);

CREATE TABLE `company`
(
    `id`         char(2)     NOT NULL COMMENT '기관 아이디',
    `name`       varchar(50) NOT NULL COMMENT '기관 이름',
    `tel`        varchar(20) NOT NULL COMMENT '운영기관 연락처',
    `operator`   varchar(50) NOT NULL COMMENT '운영기관 이름',
    `created_at` datetime    NOT NULL,
    `updated_at` datetime NULL
);

CREATE TABLE `charger`
(
    `id`                      varchar(10) NOT NULL,
    `station_id`              varchar(8)  NOT NULL,
    `charger_type`            char(2)     NOT NULL,
    `location`                varchar(200) NULL,
    `stat`                    int         NOT NULL,
    `output`                  varchar(20) NULL,
    `method`                  varchar(10) NULL,
    `stat_updated_at`         datetime NULL COMMENT '충전기 상태 변경, 통신이상, 통신복구 일시',
    `last_charge_started_at`  datetime NULL,
    `last_charge_finished_at` datetime NULL,
    `now_charge_started_at`   datetime NULL,
    `deleted_yn`              tinyint(1) NOT NULL,
    `delete_detail`           varchar(100) NULL,
    `created_at`              datetime    NOT NULL,
    `updated_at`              datetime NULL
);

ALTER TABLE `station`
    ADD CONSTRAINT `PK_STATION` PRIMARY KEY (`id`);

ALTER TABLE `company`
    ADD CONSTRAINT `PK_COMPANY` PRIMARY KEY (`id`);

ALTER TABLE `charger`
    ADD CONSTRAINT `PK_CHARGER` PRIMARY KEY (`id`);

ALTER TABLE `station`
    ADD CONSTRAINT `FK_company_TO_station_1` FOREIGN KEY (`company_id`)
        REFERENCES `company` (`id`);

ALTER TABLE `charger`
    ADD CONSTRAINT `FK_station_TO_charger_1` FOREIGN KEY (`station_id`)
        REFERENCES `station` (`id`);

