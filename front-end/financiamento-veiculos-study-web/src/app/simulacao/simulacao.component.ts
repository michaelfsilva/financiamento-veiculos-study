import { Component, OnInit } from '@angular/core';
import { DataService } from '@shared/services/data.service';
import { ResultadoSimulacao } from '@models/resultado-simulacao';
import { FinanciamentoService } from '@shared/services/financiamento.service';
import { SimulacaoFinanciamentoRequest } from '@models/simulacao-financiamento-request';
import { ParcelaResponse } from '@models/parcela-response';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AnalyticsService } from '@shared/services/analytics.service';
import {CurrencyPipe} from '@angular/common';

@Component({
  selector: 'app-simulacao',
  templateUrl: './simulacao.component.html',
  styleUrls: ['./simulacao.component.styl']
})
export class SimulacaoComponent implements OnInit {

  private readonly EVENT_CATEGORY_POC = 'poc-preco-base-risco';
  private readonly GA_EVENT = 'ga-event';

  resultadoSimulacao: ResultadoSimulacao;
  modeloVeiculo: string;
  marcaVeiculo: string;
  anoVeiculo: string;
  valorVeiculo: number;
  valorFinanciamento: number;
  valorEntrada: number;
  selectedParcela: string;
  valorParcela: number;
  parcelas: ParcelaResponse[];
  simulacaoRequest: SimulacaoFinanciamentoRequest;
  genericErrorMsg = 'Falha no processamento das informações...';
  notAllowedAccess = 'Acesso não permitido, por favor preencha os dados corretamente.';

  constructor(private dataService: DataService,
              private financiamentoService: FinanciamentoService,
              private toastr: ToastrService,
              private router: Router,
              private analyticsService: AnalyticsService,
              private currencyPipe: CurrencyPipe) {}

  ngOnInit(): void {
    this.loadVeiculoInfo();
    this.loadParcelas();
    this.analyticsOnInit();
  }

  loadVeiculoInfo(): void {
    this.dataService.currentresultadoSimulacao.subscribe(message => {
      this.verifyFinancingData(message);
      this.simulacaoRequest = message.simulacaoFinanciamentoRequest;
      this.modeloVeiculo = message.modelo;
      this.anoVeiculo = message.ano;
      this.marcaVeiculo = message.marca;
      this.valorVeiculo = message.valor;
      this.valorEntrada = message.valorEntrada;
      this.valorFinanciamento = this.valorVeiculo - this.valorEntrada;
    });
  }

  loadParcelas(): void {
    this.financiamentoService.simular(this.simulacaoRequest).subscribe({
      next: response => {
        this.parcelas = response.data.parcelas.map((data: any) => new ParcelaResponse(data.meses, data.valor));
        this.verifyFinancingData(this.parcelas);
      },
      error: error => {
        this.showErrorToaster(this.genericErrorMsg);
        console.log(error);
      },
      complete: () => {}
    });
  }

  verifyFinancingData(financiamentoData: any): void {
    if (financiamentoData === undefined || financiamentoData == null) {
      this.showErrorToaster(this.notAllowedAccess);
      this.router.navigateByUrl('/veiculo');
    }
  }

  showErrorToaster(message: string): void {
      this.toastr.error(message);
  }

  private analyticsOnInit(): void {
    this.analyticsService.pushPageView({
      event: 'ga-pageview',
      page: 'resultado-simulacao'
    });

    const formatedValorVeiculo = this.currencyPipe.transform(this.valorVeiculo, 'BRL', 'symbol', '0.2-2', 'pt');
    const eventInformation = {
      event: this.GA_EVENT,
      'event-category': this.EVENT_CATEGORY_POC,
      'event-action': 'veiculo-' + this.marcaVeiculo + '-' + this.modeloVeiculo,
      'event-label': formatedValorVeiculo
    };

    this.analyticsService.pushEvent(eventInformation);
  }
}
