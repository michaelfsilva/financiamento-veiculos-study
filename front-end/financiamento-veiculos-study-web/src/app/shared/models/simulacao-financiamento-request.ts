import { ClienteRequest } from './cliente-request';
import { SimulacaoRequest } from './simulacao-request';

export class SimulacaoFinanciamentoRequest {
  clienteRequest: ClienteRequest;
  simulacaoRequest: SimulacaoRequest;

  constructor(cliente: ClienteRequest, simulacao: SimulacaoRequest) {
    this.clienteRequest = cliente;
    this.simulacaoRequest = simulacao;
  }
}
