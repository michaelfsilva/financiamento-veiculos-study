import { RestService } from './rest.service';
import { TestBed } from '@angular/core/testing';

import { VeiculoService } from './veiculo.service';
import { HttpClientModule } from '@angular/common/http';
import { of } from 'rxjs';

describe('VeiculoService', () => {
  let service: VeiculoService;
  let restService: RestService;
  const returnMock = 'teste';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule
      ],
      providers: []
    });
    restService = TestBed.inject(RestService);
    service = TestBed.inject(VeiculoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return an observable after call the getMarcas method', () => {
    // given
    jest.spyOn(restService, 'get').mockReturnValue(of(returnMock));

    // when
    service.getMarcas();

    // then
    expect(restService.get).toBeCalled();
    expect(restService.get).toBeTruthy();
  });

  it('should return an observable after call the getModelos method', () => {
    // given
    jest.spyOn(restService, 'get').mockReturnValue(of(returnMock));

    // when
    service.getModelos(1);

    // then
    expect(restService.get).toBeCalled();
    expect(restService.get).toBeTruthy();
  });

  it('should return an observable after call the getAnos method', () => {
    // given
    jest.spyOn(restService, 'get').mockReturnValue(of({ returnMock }));

    // when
    service.getAnos(1, 2);

    // then
    expect(restService.get).toBeCalled();
    expect(restService.get).toBeTruthy();
  });

  it('should return an observable after call the getTabela method', () => {
    // given
    jest.spyOn(restService, 'get').mockReturnValue(of({ returnMock }));

    // when
    service.getTabela(1, 2, 3);

    // then
    expect(restService.get).toBeCalled();
    expect(restService.get).toBeTruthy();
  });
});
