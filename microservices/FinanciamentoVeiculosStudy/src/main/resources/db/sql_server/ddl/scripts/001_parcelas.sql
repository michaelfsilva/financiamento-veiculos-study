--liquibase formatted sql
------------------------------------------------------------------------------------------------
--Changeset create-parcelas-table:1 dbms:mssql logicalFilePath:001_parcelas.sql
--comment: create parcelas table
CREATE TABLE parcelas (
    id INT PRIMARY KEY NOT NULL,
	quantidade INT NOT NULL,
	taxa_juros NUMERIC(5,2) NOT NULL,
);

--ROLLBACK DROP TABLE parcelas;

------------------------------------------------------------------------------------------------
