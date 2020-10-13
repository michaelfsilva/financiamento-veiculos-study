import { DataService } from '@shared/services/data.service';
import { FinanciamentoService } from './../shared/services/financiamento.service';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimulacaoComponent } from './simulacao.component';
import { ToastrService } from 'ngx-toastr';
import { AppTestingModule } from '../app-testing.module';
import { APP_BASE_HREF } from '@angular/common';
import { AnalyticsService } from '@services/analytics.service';
import { ResultadoSimulacao } from '@shared/models/resultado-simulacao';
import { of, Observable } from 'rxjs';

describe('SimulacaoComponent', () => {
  let component: SimulacaoComponent;
  let fixture: ComponentFixture<SimulacaoComponent>;
  let dataService: DataService;
  let financiamentoService: FinanciamentoService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [],
      imports: [AppTestingModule],
      providers: [
        { provide: APP_BASE_HREF, useValue: '/resultado-simulacao' },
        FinanciamentoService,
        AnalyticsService,
        ToastrService,
        DataService
      ]
    }).compileComponents();
  }));

  beforeEach(() => {
    // services
    financiamentoService = TestBed.inject(FinanciamentoService);
    dataService = TestBed.inject(DataService);
    dataService.changeResultadoSimulacao(new ResultadoSimulacao('C4 Picasso',
      'Citroen', 41890, '2004', 20000, null));

    fixture = TestBed.createComponent(SimulacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load vehicle information when call the loadVeiculoInfo method', () => {
    // when
    component.loadVeiculoInfo();

    // then
    expect(component.modeloVeiculo).toBe('C4 Picasso');
    expect(component.marcaVeiculo).toBe('Citroen');
    expect(component.anoVeiculo).toBe('2004');
    expect(component.valorVeiculo).toBe(41890);
    expect(component.valorEntrada).toBe(20000);
  });

  it('should load installments information when call the loadParcelas method', () => {
    // given
    const parcela12 = { meses: 12, valor: 16061.162 };
    const responseMock = { data: { parcelas: [parcela12] } };
    jest.spyOn(financiamentoService, 'simular').mockReturnValue(of(responseMock));

    // when
    component.loadParcelas();

    // then
    expect(component.parcelas).toEqual([parcela12]);
  });

  it('should throws an error when call the loadParcelas method', () => {
    // given
    jest.spyOn(financiamentoService, 'simular').mockReturnValue(new Observable(observer => observer.error()));
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.loadParcelas();

    // then
    expect(component.showErrorToaster).toBeCalled();
  });

  it('should not throws an error when call verifyFinancingData method with valid information', () => {
    // given
    const financingData = {data: 'data'};
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.verifyFinancingData(financingData);

    // then
    expect(component.showErrorToaster).not.toBeCalled();
  });

  it('should throws an error when call verifyFinancingData method with invalid information', () => {
    // given
    jest.spyOn(component, 'showErrorToaster');

    // when
    component.verifyFinancingData(undefined);

    // then
    expect(component.showErrorToaster).toBeCalled();
  });
});
