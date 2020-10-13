--liquibase formatted sql
------------------------------------------------------------------------------------------------
--Changeset create-cliente-table:1 dbms:mssql logicalFilePath:003_cliente.sql
--comment: create cliente table
CREATE TABLE cliente (
	id INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    renda NUMERIC(8,2) NOT NULL,
    possui_imovel BIT NOT NULL,
);
--ROLLBACK DROP TABLE cliente;

------------------------------------------------------------------------------------------------
