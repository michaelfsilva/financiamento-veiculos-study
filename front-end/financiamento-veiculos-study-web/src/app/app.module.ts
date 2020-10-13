import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxMaskModule } from 'ngx-mask';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppBootstrapModule } from './bootstrap/app-bootstrap.module';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { VeiculoComponent } from './veiculo/veiculo.component';
import { SimulacaoComponent } from './simulacao/simulacao.component';
import { DataService } from '@shared/services/data.service';
import localePt from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';
registerLocaleData(localePt);


@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    VeiculoComponent,
    SimulacaoComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    CommonModule,
    AppRoutingModule,
    AppBootstrapModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgxMaskModule.forRoot()
  ],
  providers: [DataService, CurrencyPipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
