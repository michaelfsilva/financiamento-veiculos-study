--liquibase formatted sql
------------------------------------------------------------------------------------------------
--Changeset create-simulacao-financiamento-table:1 dbms:mssql logicalFilePath:003_simulacao_financiamento.sql
--comment: create simulacao financiamento table
CREATE TABLE simulacao_financiamento (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	id_cliente INT NOT NULL,
    valor_entrada NUMERIC (8,2) NOT NULL,
    valor_financiamento NUMERIC (8,2) NOT NULL,
    parcelas INT NOT NULL,
    valor_parcela NUMERIC (8,2) NOT NULL,
    data_simulacao DATETIME NOT NULL,
    FOREIGN KEY(id_cliente) REFERENCES cliente(id)
);
--ROLLBACK DROP TABLE simulacao_financiamento;

------------------------------------------------------------------------------------------------
