import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { CategoryComponent } from './components/category/category.component';
import { HomeComponent } from './components/home/home.component';
import { CategoryService } from './services/category.service';

const appRoutes: Routes = [
  {path: '', component:HomeComponent},
  {path: 'category', component:CategoryComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    CategoryComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [CategoryService],
  bootstrap: [AppComponent]
})
export class AppModule { }

