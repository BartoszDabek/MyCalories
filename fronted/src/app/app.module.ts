import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

import { AppComponent } from './app.component';
import { CategoryComponent } from './components/category/category.component';
import { HomeComponent } from './components/home/home.component';
import { ProductComponent } from './components/product/product.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { AddProductComponent } from './components/product/add-product/add-product.component';

import { Configuration } from './app.constants'
import { DataService, CustomInterceptor } from './services/data-service.service'
import { LoginService } from './services/login-service.service'
import { FilterPipe } from './pipes/filter.pipe';
import { OrderModule } from 'ngx-order-pipe';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { AlertService } from './services/alert.service';

const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'products', component: ProductComponent },
  { path: 'products/add-product', component: AddProductComponent },
  { path: 'products/:id', component: ProductDetailsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
];

@NgModule({
  declarations: [
    FilterPipe,
    AppComponent,
    CategoryComponent,
    HomeComponent,
    ProductComponent,
    ProductDetailsComponent,
    AddProductComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes),
    OrderModule
  ],
  providers: [Configuration, CookieService, AlertService, LoginService, DataService, 
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }

