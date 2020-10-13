import { Component, OnInit } from '@angular/core';
import { VeiculoService } from '@shared/services/veiculo.service';
import { DataService } from '@shared/services/data.service';
import { ResultadoSimulacao } from '@models/resultado-simulacao';
import { ClienteRequest } from '@models/cliente-request';
import { SimulacaoFinanciamentoRequest } from '@models/simulacao-financiamento-request';
import { SimulacaoRequest } from '@models/simulacao-request';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Utils } from '@shared/utils/utils';
import { AnalyticsService } from '@shared/services/analytics.service';


@Component({
  selector: 'app-veiculo',
  templateUrl: './veiculo.component.html',
  styleUrls: ['./veiculo.component.styl']
})
export class VeiculoComponent implements OnInit {

  private readonly EVENT_CATEGORY_POC = 'poc-preco-base-risco';
  private readonly EVENT_ACTION_CLICK_SIMULAR = 'click';
  private readonly EVENT_LABEL_SIMULAR = 'simular';
  private readonly GA_EVENT = 'ga-event';
  private readonly GA_PAGEVIEW = 'ga-pageview';
  private readonly PAGE_DADOS_VEICULO = 'dados-veiculo';

  clienteRequest: ClienteRequest;
  marcas = [];
  modelos = [];
  anos = [];
  valorVeiculo = '';
  valorEntrada = '';
  numeroParcelas: number;
  marcaSelected: number;
  modeloSelected: number;
  anoSelected: number;
  veiculoSelected: any;
  genericErrorMsg = 'Falha no processamento das informações...';
  notAllowedAccess = 'Acesso não permitido, por favor preencha os dados corretamente.';
  invalidValorEntradaField = 'Valor de entrada inválido.';
  valorEntradaMaiorValorVeiculo = 'Valor de entrada maior que o valor do veículo.';

  constructor(private veiculoService: VeiculoService,
              private dataService: DataService,
              private toastr: ToastrService,
              private router: Router,
              private analyticsService: AnalyticsService) { }

  ngOnInit(): void {
    this.analyticsOnInit();

    this.dataService.currentClienteRequest.subscribe(clienteRequest => this.clienteRequest = clienteRequest);

    if (!this.clienteRequest) {
      this.showErrorToaster(this.notAllowedAccess);
      this.router.navigateByUrl('/');
    }

    this.loadMarcas();
  }

  loadMarcas(): void {
    this.veiculoService.getMarcas().subscribe({
      next: response => {
        this.marcas = response.data;
      },
      error: error => {
        this.showErrorToaster(this.genericErrorMsg);
        console.log(error);
      },
      complete: () => { }
    });
  }

  onMarcaSelected(marca: number): void {
    this.marcaSelected = marca;

    this.veiculoService.getModelos(this.marcaSelected).subscribe({
      next: response => {
        this.modelos = response.data;
      },
      error: error => {
        this.showErrorToaster(this.genericErrorMsg);
        console.log(error);
      },
      complete: () => { }
    });
  }

  onModeloSelected(modelo: number): void {
    this.modeloSelected = modelo;

    this.veiculoService.getAnos(this.marcaSelected, this.modeloSelected).subscribe({
      next: response => {
        this.anos = response.data;

        const anoSelect: HTMLSelectElement = document.querySelector('#anos');
        anoSelect.selectedIndex = 0;

        this.valorVeiculo = '';
      },
      error: error => {
        this.showErrorToaster(this.genericErrorMsg);
        console.log(error);
      },
      complete: () => { }
    });
  }

  onAnoSelected(ano: number): void {
    this.anoSelected = ano;

    this.veiculoService.getTabela(this.marcaSelected, this.modeloSelected, this.anoSelected).subscribe({
      next: response => {
        this.veiculoSelected = response.data;
        this.valorVeiculo = this.veiculoSelected.preco.replace('R$ ', '');
      },
      error: error => {
        this.showErrorToaster(this.genericErrorMsg);
        console.log(error);
      },
      complete: () => { }
    });
  }

  simular(): void {
    this.analyticsSimular();

    if (this.isValidValorEntrada()) {
      const simulacaoRequest: SimulacaoFinanciamentoRequest =
        new SimulacaoFinanciamentoRequest(this.getClienteRequest(), this.getSimulacaoRequest());

      this.dataService.changeResultadoSimulacao(new ResultadoSimulacao(
        this.veiculoSelected.veiculo,
        this.veiculoSelected.marca,
        this.removeCurrency(this.valorVeiculo),
        this.veiculoSelected.anoModelo,
        this.removeCurrency(this.valorEntrada),
        simulacaoRequest));

      this.router.navigateByUrl('/resultado-simulacao');
    }
  }

  private removeCurrency(preco: any): number {
    return +preco.replace('R$', '').replace('.', '').replace(',', '.');
  }

  private formatBirthDate(birthDate: string): string {
    const result = birthDate.split('/').join('');
    return result.replace(/(\d{2})(\d{2})(\d{4})/, '$3-$2-$1');
  }

  private getClienteRequest(): ClienteRequest {
    return new ClienteRequest(this.clienteRequest.nome,
      this.clienteRequest.cpf,
      this.formatBirthDate(this.clienteRequest.dataNascimento),
      this.clienteRequest.telefone,
      this.removeCurrency(this.clienteRequest.renda),
      this.clienteRequest.possuiImovel);
  }

  private getSimulacaoRequest(): SimulacaoRequest {
    return new SimulacaoRequest(
      this.removeCurrency(this.valorEntrada.toString()),
      this.removeCurrency(this.valorVeiculo.toString()));
  }

  showErrorToaster(message: string): void {
    this.toastr.error(message);
  }

  inputCurrency(input: any): string {
    return Utils.inputCurrency(input);
  }

  private analyticsSimular(): void {
    const eventInformation = {
      event: this.GA_EVENT,
      'event-category': this.EVENT_CATEGORY_POC,
      'event-action': this.EVENT_ACTION_CLICK_SIMULAR,
      'event-label': this.EVENT_LABEL_SIMULAR
    };

    this.analyticsService.pushEvent(eventInformation);
  }

  private analyticsOnInit(): void {
    this.analyticsService.pushPageView({
      event: this.GA_PAGEVIEW,
      page: this.PAGE_DADOS_VEICULO
    });
  }

  private isValidValorEntrada(): boolean {
    if (this.removeCurrency(this.valorEntrada) > this.removeCurrency(this.valorVeiculo)) {
      this.toastr.error(this.valorEntradaMaiorValorVeiculo);
      return false;
    }

    if (this.valorEntrada) {
      return true;
    }

    this.toastr.error(this.invalidValorEntradaField);
    return false;
  }
}
