import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainComponent } from './main/main.component';
import { VeiculoComponent } from './veiculo/veiculo.component';
import { SimulacaoComponent } from './simulacao/simulacao.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent
  },
  {
    path: 'veiculo',
    component: VeiculoComponent
  },
  {
    path: 'resultado-simulacao',
    component: SimulacaoComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
