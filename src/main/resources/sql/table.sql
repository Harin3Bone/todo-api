CREATE TABLE `card`
(
    `id`                  VARCHAR(36)                        NOT NULL DEFAULT '' COMMENT 'Unique id for card',
    `topic`               VARCHAR(100)                       NOT NULL COMMENT 'Topic card name',
    `content`             TEXT                               NOT NULL COMMENT 'Description of card',
    `priority`            INT                                NOT NULL DEFAULT '0' COMMENT 'Level priority of card',
    `status`              ENUM ('todo','in_progress','done') NOT NULL DEFAULT 'todo' COMMENT 'Mark for checking is job done or in progress',
    `remove_status`       TINYINT(1)                         NOT NULL DEFAULT '0' COMMENT 'Mark for checking is in trash or on board',
    `created_timestamp`   TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp at create without timezone',
    `modified_timestamp`  TIMESTAMP                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp at update without timezone',
    `completed_timestamp` TIMESTAMP                          NULL     DEFAULT NULL COMMENT 'Timestamp at finished without timezone',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
