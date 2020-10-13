--liquibase formatted sql
------------------------------------------------------------------------------------------------
--Changeset insert-parcelas-table:1 dbms:mssql logicalFilePath:001_parcelas.sql
--comment: insert initials parcelas data
INSERT INTO parcelas (id, quantidade, taxa_juros)
VALUES (1, 12, 0.0),
	   (2, 24, 0.3),
	   (3, 36, 0.6),
	   (4, 48, 1.0),
	   (5, 60, 1.5);

--ROLLBACK DELETE FROM parcelas WHERE ID BETWEEN 1 AND 5;

------------------------------------------------------------------------------------------------
