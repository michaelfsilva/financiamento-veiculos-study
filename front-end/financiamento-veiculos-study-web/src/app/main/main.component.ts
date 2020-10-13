import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { DataService } from '../shared/services/data.service';
import { ClienteRequest } from '../shared/models/cliente-request';
import { Router } from '@angular/router';
import { Utils } from '../shared/utils/utils';
import { AnalyticsService } from 'src/app/shared/services/analytics.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.styl']
})
export class MainComponent implements OnInit {
  clienteForm: FormGroup;
  dataNascimentoMsg = 'Preencha a sua data de nascimento';
  rendaMsg = 'Preencha a sua renda';

  private readonly EVENT_CATEGORY_POC = 'poc-preco-base-risco';
  private readonly EVENT_ACTION_CLICK_PROXIMO = 'click';
  private readonly EVENT_LABEL_PROXIMO = 'proximo';
  private readonly GA_EVENT = 'ga-event';
  private readonly GA_PAGEVIEW = 'ga-pageview';
  private readonly PAGE_DADOS_PESSOAIS = 'dados-pessoais';

  ngOnInit(): void {
    this.analyticsOnInit();

    this.clienteForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [
        Validators.required,
        Validators.pattern(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/)
      ]],
      dataNascimento: ['', Validators.required],
      telefone: ['', [
        Validators.required,
        Validators.pattern(/^\(\d{2}\)[ ]?\d{4,5}\-\d{3,4}$/)
      ]],
      renda: ['', Validators.required],
      possuiImovel: [false]
    });
  }

  get clienteFormControl(): any {
    return this.clienteForm.controls;
  }

  constructor(private fb: FormBuilder,
              private data: DataService,
              private router: Router,
              private analyticsService: AnalyticsService) {
  }

  submit(): void {
    this.analyticsProximo();

    this.clienteForm.markAllAsTouched();
    this.isValidBirthDate();
    this.isValidRenda();

    if (this.clienteForm.valid) {
      this.sendClienteInfoToTheNextComponent();
      this.router.navigateByUrl('/veiculo');
    }
  }

  private sendClienteInfoToTheNextComponent(): void {
    this.data.changeClienteRequest(new ClienteRequest(
      this.clienteForm.value.nome,
      this.clienteForm.value.cpf,
      this.clienteForm.value.dataNascimento,
      this.clienteForm.value.telefone,
      this.clienteForm.value.renda,
      this.clienteForm.value.possuiImovel
    ));
  }

  inputCurrency(input: any): string {
    return Utils.inputCurrency(input);
  }

  private analyticsProximo(): void {
    const eventInformation = {
      event: this.GA_EVENT,
      'event-category': this.EVENT_CATEGORY_POC,
      'event-action': this.EVENT_ACTION_CLICK_PROXIMO,
      'event-label': this.EVENT_LABEL_PROXIMO
    };

    this.analyticsService.pushEvent(eventInformation);
  }

  private analyticsOnInit(): void {
    this.analyticsService.pushPageView({
      event: this.GA_PAGEVIEW,
      page: this.PAGE_DADOS_PESSOAIS
    });
  }

  isValidBirthDate(): void {
    if (this.clienteForm.value.dataNascimento === '') {
      return;
    }

    const today: any = new Date();
    const inputDate: any = new Date(this.clienteForm.value.dataNascimento);

    if (inputDate.getTime() > today.getTime()) {
      this.clienteFormControl.dataNascimento.setErrors({incorrect: true});
      this.dataNascimentoMsg = 'Data selecionada maior que a data atual';
      return;
    }

    const diffTimeInMilliseconds = Math.abs(inputDate - today);
    const diffYears = Math.ceil(diffTimeInMilliseconds / 31556952000) - 1;

    if (diffYears < 18) {
      this.clienteFormControl.dataNascimento.setErrors({incorrect: true});
      this.dataNascimentoMsg = 'Idade minima de 18 anos';
    }
  }

  isValidRenda(): void {
    const renda = +this.clienteForm.value.renda.replace(',', '.');

    if (this.clienteForm.value.renda !== '' && renda < 100) {
      this.clienteFormControl.renda.setErrors({incorrect: true});
      this.rendaMsg = 'Renda minima de R$100,00';
    }
  }
}
