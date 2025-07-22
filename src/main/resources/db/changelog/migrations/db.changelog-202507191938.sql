--liquibase formatted sql
--changeset ArthDroid:202507191938
--comment: Criação da tabela BOARDS

CREATE TABLE BOARDS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS;
