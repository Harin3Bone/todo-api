-- Table Script
CREATE TABLE IF NOT EXISTS card
(
    id                  VARCHAR(36) CHARSET utf8mb4          DEFAULT ''                NOT NULL COMMENT 'Unique id for card'
        PRIMARY KEY,
    topic               VARCHAR(100) CHARSET utf8mb4                                   NOT NULL COMMENT 'Topic card name',
    content             TEXT CHARSET utf8mb4                                           NOT NULL COMMENT 'Description of card',
    priority            INT                                  DEFAULT 0                 NOT NULL COMMENT 'Level priority of card',
    status              ENUM ('todo', 'in_progress', 'done') DEFAULT 'todo'            NOT NULL COMMENT 'Mark for checking is job done or in progress',
    remove_status       TINYINT(1)                           DEFAULT 0                 NOT NULL COMMENT 'Mark for checking is in trash or on board',
    created_timestamp   TIMESTAMP                            DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'Timestamp at create without timezone',
    modified_timestamp  TIMESTAMP                            DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Timestamp at update without timezone',
    completed_timestamp TIMESTAMP                                                      NULL COMMENT 'Timestamp at finished without timezone'
);
