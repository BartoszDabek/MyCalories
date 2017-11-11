import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutes } from './app.routes';
import { Configuration } from './app.constants'

import { AppComponent } from './app.component';
import { CategoryComponent } from './components/category/category.component';
import { HomeComponent } from './components/home/home.component';
import { ProductComponent } from './components/product/product.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AddProductComponent } from './components/product/add-product/add-product.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SummaryComponent } from './components/summary/summary.component';
import { MealsDetailsComponent } from './components/summary/meals-details/meals-details.component';

import { OrderModule } from 'ngx-order-pipe';
import { FilterPipe } from './pipes/filter.pipe';
import { SortPipe } from './pipes/sort.pipe';

import { SharedModule } from './shared/shared.module';


@NgModule({
  declarations: [
    FilterPipe,
    SortPipe,
    AppComponent,
    CategoryComponent,
    HomeComponent,
    ProductComponent,
    ProductDetailsComponent,
    AddProductComponent,
    LoginComponent,
    RegistrationComponent,
    SummaryComponent,
    MealsDetailsComponent,
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutes,
    OrderModule,
    SharedModule,
  ],
  providers: [Configuration],
  bootstrap: [AppComponent]
})
export class AppModule { }

