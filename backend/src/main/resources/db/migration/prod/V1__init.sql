CREATE TABLE `category` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `drink` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `alcohol_by_volume` double DEFAULT NULL,
    `english_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `image_file_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `preference_avg` double DEFAULT NULL,
    `category_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK786l2oh7sdk11cfjh1v2uvn0t` (`category_id`),
CONSTRAINT `FK786l2oh7sdk11cfjh1v2uvn0t` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `member` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `biography` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `modified_at` datetime(6) DEFAULT NULL,
    `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `provide_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `provider_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `preference` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) DEFAULT NULL,
    `modified_at` datetime(6) DEFAULT NULL,
    `rate` double NOT NULL,
    `drink_id` bigint DEFAULT NULL,
    `member_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKi07nu055knldblwysf2x0rg22` (`drink_id`),
    KEY `FKg1k85wg4bngr1y82ahkbyyj2y` (`member_id`),
    CONSTRAINT `FKg1k85wg4bngr1y82ahkbyyj2y` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FKi07nu055knldblwysf2x0rg22` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `review` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) DEFAULT NULL,
    `modified_at` datetime(6) DEFAULT NULL,
    `content` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL,
    `drink_id` bigint DEFAULT NULL,
    `member_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKfhrc1rittn0kyouv8lq0527w8` (`drink_id`),
    KEY `FKk0ccx5i4ci2wd70vegug074w1` (`member_id`),
    CONSTRAINT `FKfhrc1rittn0kyouv8lq0527w8` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`),
    CONSTRAINT `FKk0ccx5i4ci2wd70vegug074w1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;