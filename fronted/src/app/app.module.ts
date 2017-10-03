import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { CategoryComponent } from './components/category/category.component';
import { HomeComponent } from './components/home/home.component';
import { ProductComponent } from './components/product/product.component';


import { CategoryService } from './services/category.service';
import { ProductService } from './services/product.service';
import { FilterPipe } from './pipes/filter.pipe';

const appRoutes: Routes = [
  {path: '', component:HomeComponent},
  {path: 'category', component:CategoryComponent},
  {path: 'products', component: ProductComponent}
];

@NgModule({
  declarations: [
    FilterPipe,
    AppComponent,
    CategoryComponent,
    HomeComponent,
    ProductComponent
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [CategoryService, ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }

