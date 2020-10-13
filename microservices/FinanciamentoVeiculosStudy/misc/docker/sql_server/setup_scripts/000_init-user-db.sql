CREATE DATABASE financiamento_veiculos
GO
USE financiamento_veiculos
GO
CREATE LOGIN veiculos WITH PASSWORD='Fin.veiculos', DEFAULT_DATABASE=financiamento_veiculos, CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
EXEC financiamento_veiculos..sp_addsrvrolemember @loginame = N'veiculos', @rolename = N'sysadmin'
GO
