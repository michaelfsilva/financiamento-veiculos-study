import { SimulacaoFinanciamentoRequest } from '@models/simulacao-financiamento-request';
import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { FinanciamentoService } from './financiamento.service';
import { RestService } from './rest.service';
import { of } from 'rxjs';

describe('FinanciamentoService', () => {
  let service: FinanciamentoService;
  let restService: RestService;
  const returnMock = 'teste';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
      ],
      providers: []
    });
    restService = TestBed.inject(RestService);
    service = TestBed.inject(FinanciamentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call the rest service when call simular method', () => {
    // given
    jest.spyOn(restService, 'post').mockReturnValue(of({ returnMock }));

    // when
    const simulacaoRequestMock = new SimulacaoFinanciamentoRequest(null, null);
    service.simular(simulacaoRequestMock);

    // then
    expect(restService.post).toBeCalled();
    expect(restService.post).toBeTruthy();
  });
});
