import { TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { RestService } from './rest.service';
import { of } from 'rxjs';

describe('RestService', () => {
  let service: RestService;
  let httpClient: HttpClient;
  const returnMock = 'teste';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
      ],
      providers: [
        HttpClient
      ]
    });
    httpClient = TestBed.inject(HttpClient);
    service = TestBed.inject(RestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('httpClient get method should be called after the rest service get method', () => {
    // given
    jest.spyOn(httpClient, 'get').mockReturnValue(of({ returnMock }));

    // when
    service.get('www.teste.com/teste');

    // then
    expect(httpClient.get).toBeCalled();
  });

  it('httpClient delete method should be called after the rest service delete method', () => {
    // given
    jest.spyOn(httpClient, 'delete').mockReturnValue(of({ returnMock }));

    // when
    service.delete('www.teste.com/teste', 1);

    // then
    expect(httpClient.delete).toBeCalled();
  });

  it('httpClient post method should be called after the rest service post method', () => {
    // given
    jest.spyOn(httpClient, 'post').mockReturnValue(of({ returnMock }));

    // when
    service.post('www.teste.com/teste', { nome: 'teste' });

    // then
    expect(httpClient.post).toBeCalled();
  });

  it('httpClient patch method should be called after the rest service patch method', () => {
    // given
    jest.spyOn(httpClient, 'patch').mockReturnValue(of({ returnMock }));

    // when
    service.patch('www.teste.com/teste', 1, { nome: 'teste' });

    // then
    expect(httpClient.patch).toBeCalled();
  });

});
