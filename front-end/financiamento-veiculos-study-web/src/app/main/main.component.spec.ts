import { Utils } from './../shared/utils/utils';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MainComponent } from './main.component';
import { AppTestingModule } from '../app-testing.module';
import {APP_BASE_HREF} from '@angular/common';

describe('MainComponent', () => {
  let component: MainComponent;
  let fixture: ComponentFixture<MainComponent>;
  const sendClienteInfoToTheNextComponent = 'sendClienteInfoToTheNextComponent';

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [],
      imports: [AppTestingModule],
      providers: [{provide: APP_BASE_HREF, useValue: '/'}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return an error message if the income is 1.00', () => {
    // given
    component.clienteForm.value.renda = '1.00';

    // when
    component.isValidRenda();

    // then
    expect(component.rendaMsg).toContain('Renda minima de R$100,00');
  });

  it('should verify if the income has empty value', () => {
    // given
    component.clienteForm.value.renda = '';

    // when
    component.isValidRenda();

    // then
    expect(component.rendaMsg).toContain('');
  });

  it('should verify if the income of value 10,000.90 is valid', () => {
    // given
    component.clienteForm.value.renda = '10,000.90';

    // when
    component.isValidRenda();

    // then
    expect(component.rendaMsg).toContain('');
  });

  it('should return an error message if birthdate is greater than the current date', () => {
    // given
    component.clienteForm.value.dataNascimento = '3000-12-12';

    // when
    component.isValidBirthDate();

    // then
    expect(component.dataNascimentoMsg).toContain('Data selecionada maior que a data atual');
  });

  it('should return an error message if the client is underage', () => {
    // given
    component.clienteForm.value.dataNascimento = '2004-12-12';

    // when
    component.isValidBirthDate();

    // then
    expect(component.dataNascimentoMsg).toContain('Idade minima de 18 anos');
  });

  it('should not return an error message if client has the right range of age', () => {
    // given
    component.clienteForm.value.dataNascimento = '1970-12-12';

    // when
    component.isValidBirthDate();

    // then
    expect(component.dataNascimentoMsg).not.toContain('Idade minima de 18 anos');
  });

  it('should not return an error message if the birthdate is empty', () => {
    // given
    component.clienteForm.value.dataNascimento = '';

    // when
    component.isValidBirthDate();

    // then
    expect(component.dataNascimentoMsg).toContain('');
  });

  it('should call verfication methods when submit information with a valid form', () => {
    // given
    jest.spyOn(component.clienteForm, 'valid', 'get').mockReturnValue(true);
    jest.spyOn<any, any>(component, 'sendClienteInfoToTheNextComponent');
    jest.spyOn(component, 'isValidBirthDate');
    jest.spyOn(component, 'isValidRenda');

    // when
    component.submit();

    // then
    expect(component.isValidBirthDate).toBeCalled();
    expect(component.isValidRenda).toBeCalled();
    expect(component[sendClienteInfoToTheNextComponent]).toHaveBeenCalled();
  });

  it('should not send information to next component when submit information with invalid form', () => {
    // given
    jest.spyOn(component.clienteForm, 'valid', 'get').mockReturnValue(false);
    jest.spyOn<any, any>(component, 'sendClienteInfoToTheNextComponent');

    // when
    component.submit();

    // then
    expect(component[sendClienteInfoToTheNextComponent]).not.toHaveBeenCalled();
  });

  it('should use Utils static class when input currency', () => {
    // given
    jest.spyOn(Utils, 'inputCurrency');

    // when
    component.inputCurrency('123');

    // then
    expect(Utils.inputCurrency).toBeCalled();
  });
});
