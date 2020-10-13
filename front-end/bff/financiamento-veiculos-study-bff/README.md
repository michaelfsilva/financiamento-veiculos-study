# Financiamento-Veiculos-BFF (BackEnd-For-FrontEnd)
API para processar dados para o front end

### API Documentations

http://localhost:8000/swagger

### API endpoints
GET http://localhost:8000/api/veiculos/marcas [lista todas marcas]  
GET http://localhost:8000/api/veiculos/modelos/:marcaId [lista modelos pelo id da marca]  
GET http://localhost:8000/api/veiculos/modelos/:marcaId/:modeloId [lista os anos por marca de modelo]  
GET http://localhost:8000/api/veiculos/modelos/:marcaId/:modeloId/:ano [retorna as informações da tabela fipe]
POST http://localhost:8000/api/financiamento/simular [simulação de financiamento]

Username: financiamento  
Password: veiculos
