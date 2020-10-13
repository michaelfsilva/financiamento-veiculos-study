import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ResultadoSimulacao } from '@models/resultado-simulacao';
import { ClienteRequest } from '@models/cliente-request';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  resultadoSimulacao: ResultadoSimulacao;
  clienteRequest: ClienteRequest;

  private resultadoSimulacaoSource = new BehaviorSubject(this.resultadoSimulacao);
  currentresultadoSimulacao = this.resultadoSimulacaoSource.asObservable();

  private clienteSource = new BehaviorSubject(this.clienteRequest);
  currentClienteRequest = this.clienteSource.asObservable();

  constructor() { }

  changeResultadoSimulacao(financiamentoRequest: any): any {
    this.resultadoSimulacaoSource.next(financiamentoRequest);
  }

  changeClienteRequest(clienteRequest: any): any {
    this.clienteSource.next(clienteRequest);
  }
}
