import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RestService } from './rest.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VeiculoService {

  constructor(private restService: RestService) {}

  private url = environment.api;

  public getMarcas(): Observable<any> {
    return this.restService.get(this.url.marcas);
  }

  public getModelos(marcaId: number): Observable<any> {
    return this.restService.get(`${this.url.modelos}/${marcaId}`);
  }

  public getAnos(marcaId: number, modeloId: number): Observable<any> {
    return this.restService.get(`${this.url.modelos}/${marcaId}/${modeloId}`);
  }

  public getTabela(marcaId: number, modeloId: number, anoId: number): Observable<any> {
    return this.restService.get(`${this.url.modelos}/${marcaId}/${modeloId}/${anoId}`);
  }
}
