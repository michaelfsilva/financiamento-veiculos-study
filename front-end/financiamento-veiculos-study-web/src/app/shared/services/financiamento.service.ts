import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RestService } from './rest.service';
import { environment } from 'src/environments/environment';
import { SimulacaoFinanciamentoRequest } from '@models/simulacao-financiamento-request';

@Injectable({
  providedIn: 'root'
})
export class FinanciamentoService {

  constructor(private restService: RestService) {}

  private url = environment.api;

  public simular(simulacaoFinanciamento: SimulacaoFinanciamentoRequest): Observable<any> {
    return this.restService.post(`${this.url.simular}`, simulacaoFinanciamento);
  }
}
