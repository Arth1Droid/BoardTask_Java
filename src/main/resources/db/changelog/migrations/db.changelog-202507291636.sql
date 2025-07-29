--liquibase formatted sql
--changeset ArthDroid:202507191938
--comment: boards_colums table create

CREATE TABLE BOARDS_COLUMS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `order` int NOT NULL,
    kind VARCHAR(7) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_columns_fk FOREIGN KEY (board_id) REFERENCES BOARDS(id) ON DELETE CASCADE
    CONSTRAINT id_order_uk UNIQUE KEY unique_board_id_order (board_id, `order`)
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS_COLUMNS;
