import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { CategoryComponent } from './components/category/category.component';
import { ProductComponent } from './components/product/product.component';
import { AddProductComponent } from './components/product/add-product/add-product.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SummaryComponent } from './components/summary/summary.component';

const appRoutes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'home', component: HomeComponent },
    { path: 'category', component: CategoryComponent },
    { path: 'products', component: ProductComponent },
    { path: 'products/add-product', component: AddProductComponent },
    { path: 'products/:id', component: ProductDetailsComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegistrationComponent },
    { path: 'summary', component: SummaryComponent },
  ];

export const AppRoutes = RouterModule.forRoot(appRoutes, { preloadingStrategy: PreloadAllModules });