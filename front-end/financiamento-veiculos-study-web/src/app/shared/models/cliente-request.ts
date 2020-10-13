export class ClienteRequest {
  nome: string;
  cpf: string;
  dataNascimento: string;
  telefone: string;
  renda: number;
  possuiImovel: boolean;

  constructor(nome: string, cpf: string, dataNascimento: string, telefone: string, renda: number, possuiImovel: boolean) {
    this.nome = nome;
    this.cpf = cpf;
    this.dataNascimento = dataNascimento;
    this.telefone = telefone;
    this.renda = renda;
    this.possuiImovel = possuiImovel;
  }
}
