--liquibase formatted sql
------------------------------------------------------------------------------------------------
--Changeset create-score-table:1 dbms:mssql logicalFilePath:003_score.sql
--comment: create score table
CREATE TABLE score (
    id INT PRIMARY KEY NOT NULL,
	faixa VARCHAR(10) NOT NULL,
	pontuacao INT NOT NULL,
	juros NUMERIC(5,2) NOT NULL
);
--ROLLBACK DROP TABLE score;

------------------------------------------------------------------------------------------------
