# language: pt
@SimularFinanciamento
Funcionalidade: Validar fluxo de simulação

Esquema do Cenario: Validar fluxo de simulação com usuário válido
    Dado que estou acessando a aplicacao
    E insiro o nome <nome>, cpf <cpf>, data de nascimento <dataNascimento>, telefone <telefone> e renda <renda> do cliente
    E escolho o veiculo e insiro o valor de entrada
    Quando clico em simular
    Entao a pagina retorna o resultado da simulacao

  Exemplos:
  |   nome   |        cpf       |   dataNascimento   |     telefone   |    renda   |
  | "Fulano" | "000.000.000-00" |     "01011998"     |   "1999639135" |  "5680.00" |
