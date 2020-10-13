export class SimulacaoRequest {
  valorEntrada: number;
  valorVeiculo: number;

  constructor(valorEntrada: number, valorVeiculo: number) {
    this.valorEntrada = valorEntrada;
    this.valorVeiculo = valorVeiculo;
  }
}
