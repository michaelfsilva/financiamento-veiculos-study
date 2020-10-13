import { ClienteRequest } from '@models/cliente-request';
import { DataService } from '@services/data.service';
import { ToastrService } from 'ngx-toastr';
import { VeiculoService } from '@services/veiculo.service';
import { Observable } from 'rxjs';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VeiculoComponent } from './veiculo.component';
import { AppTestingModule } from '../app-testing.module';
import { APP_BASE_HREF } from '@angular/common';
import { Utils } from '@shared/utils/utils';
import { of } from 'rxjs';
import { AnalyticsService } from '@services/analytics.service';

describe('VeiculoComponent', () => {
  let component: VeiculoComponent;
  let fixture: ComponentFixture<VeiculoComponent>;
  let veiculoService: VeiculoService;
  let analyticsService: AnalyticsService;
  let toastrService: ToastrService;
  let dataService: DataService;

  const removeCurrency = 'removeCurrency';
  const error = 'error';
  const analyticsSimular = 'analyticsSimular';
  const getClienteRequest = 'getClienteRequest';
  const getSimulacaoRequest = 'getSimulacaoRequest';

  const clienteRequestMock = new ClienteRequest('João', '123.123.123-12',
    '10/10/1990', '(00) 0000-0000', 5000, false);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [],
      imports: [AppTestingModule],
      providers: [
        { provide: APP_BASE_HREF, useValue: '/veiculo' },
        VeiculoService,
        AnalyticsService,
        ToastrService
      ]
    }).compileComponents();
  }));

  beforeEach(() => {

    // services
    veiculoService = TestBed.inject(VeiculoService);
    analyticsService = TestBed.inject(AnalyticsService);
    toastrService = TestBed.inject(ToastrService);
    dataService = TestBed.inject(DataService);

    fixture = TestBed.createComponent(VeiculoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not simulate if valorEntrada field is greater than valorVeiculo field', () => {
    // given
    component.valorEntrada = 'R$ 13,000';
    component.valorVeiculo = 'R$ 10,000';
    jest.spyOn(toastrService, 'error').mockReturnThis();
    jest.spyOn(analyticsService, 'pushPageView').mockReturnThis();
    jest.spyOn<any, any>(component, 'removeCurrency');

    // when
    component.simular();

    // then
    expect(component[removeCurrency]).toBeCalledTimes(2);
    expect(toastrService[error]).toBeCalled();
  });

  it('should not simulate if valorEntrada is empty', () => {
    // given
    component.valorEntrada = '';
    jest.spyOn(toastrService, 'error').mockReturnThis();
    jest.spyOn(analyticsService, 'pushPageView').mockReturnThis();
    jest.spyOn<any, any>(component, 'removeCurrency');

    // when
    component.simular();

    // then
    expect(component[removeCurrency]).toBeCalledTimes(2);
    expect(toastrService[error]).toBeCalled();
  });

  it('should simulate if form values are valid', () => {
    // given
    component.valorEntrada = 'R$ 3,000';
    component.valorVeiculo = 'R$ 10,000';
    jest.spyOn(analyticsService, 'pushPageView').mockReturnThis();
    jest.spyOn<any, any>(component, 'removeCurrency').mockReturnValue(5000);
    jest.spyOn<any, any>(component, 'analyticsSimular');
    jest.spyOn<any, any>(component, 'getClienteRequest');
    jest.spyOn<any, any>(component, 'getSimulacaoRequest');

    // and
    component.clienteRequest = clienteRequestMock;
    component.veiculoSelected = {
      veiculo: '',
      marca: '',
      valorVeiculo: 'R$ 23,000',
      anoModelo: '2001'
    };

    // when
    component.simular();

    // then
    expect(component[removeCurrency]).toBeCalledTimes(7);
    expect(component[analyticsSimular]).toBeCalled();
    expect(component[getClienteRequest]).toBeCalled();
    expect(component[getSimulacaoRequest]).toBeCalled();
  });

  it('should use Utils static class when input currency', () => {
    // given
    jest.spyOn(Utils, 'inputCurrency');

    // when
    component.inputCurrency('123');

    // then
    expect(Utils.inputCurrency).toBeCalled();
  });

  it('should load marcas when call the method loadMarcas', () => {
    // given
    const responseMock = {
      data: [{
        nome: 'Marca',
        id: 6,
      }]
    };

    // and
    jest.spyOn(veiculoService, 'getMarcas').mockReturnValue(of(responseMock));
    jest.spyOn(component, 'showErrorToaster');
    const expectedMarcas = [{ nome: 'Marca', id: 6 }];

    // when
    component.loadMarcas();

    // then
    expect(component.showErrorToaster).not.toBeCalled();
    expect(component.marcas).toStrictEqual(expectedMarcas);
  });

  it('should load modelos when call the method onMarcaSelected', () => {
    // given
    const responseMock = {
      data: [{
        nome: 'Modelo',
        id: 6,
      }]
    };
    jest.spyOn(veiculoService, 'getModelos').mockReturnValue(of(responseMock));
    jest.spyOn(component, 'showErrorToaster');
    const expectedModelos = [{ nome: 'Modelo', id: 6 }];

    // when
    component.onMarcaSelected(1);

    // then
    expect(component.showErrorToaster).not.toBeCalled();
    expect(component.modelos).toStrictEqual(expectedModelos);
  });

  it('should load anos when call the method onModeloSelected', () => {
    // given
    const responseMock = {
      data: [{
        ano: '2007',
        id: 6,
      }]
    };
    jest.spyOn(veiculoService, 'getAnos').mockReturnValue(of(responseMock));
    jest.spyOn(component, 'showErrorToaster');
    const expectedAnos = [{ ano: '2007', id: 6 }];

    // when
    component.onModeloSelected(1);

    // then
    expect(component.showErrorToaster).not.toBeCalled();
    expect(component.anos).toStrictEqual(expectedAnos);
  });

  it('should load valor veiculo when call the method onAnoSelected', () => {
    // given
    const responseMock = {
      data: {
        preco: 'R$ 23000'
      }
    };
    jest.spyOn(veiculoService, 'getTabela').mockReturnValue(of(responseMock));
    jest.spyOn(component, 'showErrorToaster');
    const expectedAnos = { preco: 'R$ 23000' };

    // when
    component.onAnoSelected(1);

    // then
    expect(component.showErrorToaster).not.toBeCalled();
    expect(component.veiculoSelected).toStrictEqual(expectedAnos);
    expect(component.valorVeiculo).toStrictEqual('23000');
  });

  it('should throws an error when call the method onMarcaSelected', () => {
    // given
    jest.spyOn(veiculoService, 'getModelos').mockReturnValue(new Observable(observer => observer.error()));
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.onMarcaSelected(1);

    // then
    expect(component.showErrorToaster).toBeCalled();
  });

  it('should throws an error when call the method onModeloSelected', () => {
    // given
    jest.spyOn(veiculoService, 'getAnos').mockReturnValue(new Observable(observer => observer.error()));
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.onModeloSelected(1);

    // then
    expect(component.showErrorToaster).toBeCalled();
  });

  it('should throws an error when call the method onAnoSelected', () => {
    // given
    jest.spyOn(veiculoService, 'getTabela').mockReturnValue(new Observable(observer => observer.error()));
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.onAnoSelected(1);

    // then
    expect(component.showErrorToaster).toBeCalled();
  });

  it('should throws an error when call the method loadMarcas', () => {
    // given
    jest.spyOn(veiculoService, 'getMarcas').mockReturnValue(new Observable(observer => observer.error()));
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.loadMarcas();

    // then
    expect(component.showErrorToaster).toBeCalled();
  });

  it('should not throws an error when call the method ngOnInit method if cliente request is valid', () => {
    // given
    jest.spyOn<any, any>(component, 'analyticsOnInit');
    dataService.currentClienteRequest = new Observable();
    jest.spyOn(component, 'showErrorToaster');
    component.clienteRequest = clienteRequestMock;

    // when
    component.ngOnInit();

    // then
    expect(component.showErrorToaster).not.toBeCalled();
  });
});
