import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'Basic ' + btoa('financiamento:veiculos')
    })
  };

  constructor(private http: HttpClient) { }

  public get(url: string): Observable<any> {
    return this.http.get(url, this.httpOptions);
  }

  public delete(url: string, id: number): Observable<any> {
    return this.http.delete(`${url}/${id}`, this.httpOptions);
  }

  public post(url: string, params: object): Observable<object> {
    return this.http.post(url, params, this.httpOptions);
  }

  public patch(url: string, id: number, params: object): Observable<object> {
    return this.http.patch(`${url}/${id}`, params, this.httpOptions);
  }
}
