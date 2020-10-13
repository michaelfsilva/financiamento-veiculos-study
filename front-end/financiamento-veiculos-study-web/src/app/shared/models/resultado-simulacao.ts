import { SimulacaoFinanciamentoRequest } from './simulacao-financiamento-request';

export class ResultadoSimulacao {
  modelo: string;
  marca: string;
  valor: number;
  ano: string;
  valorEntrada: any;
  simulacaoFinanciamentoRequest: SimulacaoFinanciamentoRequest;

  constructor(modelo: string, marca: string, valor: number, ano: string,
              valorEntrada: number, simulacaoFinanciamentoRequest: SimulacaoFinanciamentoRequest) {
    this.marca = marca;
    this.modelo = modelo;
    this.valor = valor;
    this.ano = ano;
    this.valorEntrada = valorEntrada;
    this.simulacaoFinanciamentoRequest = simulacaoFinanciamentoRequest;
  }
}
