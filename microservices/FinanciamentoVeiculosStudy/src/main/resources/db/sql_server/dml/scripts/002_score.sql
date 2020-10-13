--liquibase formatted sql
------------------------------------------------------------------------------------------------
--Changeset insert-score-table:1 dbms:mssql logicalFilePath:002_score.sql
--comment: insert initials score data
INSERT INTO score(id, faixa, pontuacao, juros)
VALUES (1, 'Baixo', 3, 1.1),
	   (2, 'Baixo', 4, 1.1),
	   (3, 'Medio', 5, 5.21),
	   (4, 'Medio', 6, 5.21),
	   (5, 'Medio', 7, 5.21),
	   (6, 'Alto', 8, 9.34),
	   (7, 'Alto', 9, 9.34);

--ROLLBACK DELETE FROM score WHERE ID BETWEEN 1 AND 7;

------------------------------------------------------------------------------------------------
