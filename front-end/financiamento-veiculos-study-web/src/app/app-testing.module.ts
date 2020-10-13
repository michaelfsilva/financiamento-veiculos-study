import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppModule } from './app.module';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

@NgModule({
    imports: [
      BrowserModule,
      HttpClientModule,
      BrowserAnimationsModule,
      AppModule,
      FormsModule,
      ReactiveFormsModule,
      AppRoutingModule,
      ToastrModule.forRoot(),
    ],
    declarations: [],
    providers: []
  })
  export class AppTestingModule {}
