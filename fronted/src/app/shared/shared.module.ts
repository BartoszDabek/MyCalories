import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NavbarComponent } from './components/navbar/navbar.component';
import { CustomInterceptor, DataService } from './services/data-service.service';
import { LoginService } from './services/login-service.service'

@NgModule({
    imports: [CommonModule, RouterModule],
    exports: [NavbarComponent],
    declarations: [NavbarComponent],
    providers: [DataService, LoginService, 
        {
            provide: HTTP_INTERCEPTORS,
            useClass: CustomInterceptor,
            multi: true,
        }],
})

export class SharedModule { }