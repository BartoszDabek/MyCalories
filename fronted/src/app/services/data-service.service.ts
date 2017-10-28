import { HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Configuration } from '../app.constants';
import { LoginService} from './login-service.service'
import { CookieService } from 'ngx-cookie-service';


@Injectable()
export class DataService {

    private actionUrl: string;

    constructor(private http: HttpClient) {
        this.actionUrl = Configuration.HOME_URL;
    }

    public getAll<T>(directSource: string): Observable<T> {
        return this.http.get<T>(this.actionUrl + directSource);
    }

    public getSingle<T>(directSource: string, id: number): Observable<T> {
        return this.http.get<T>(this.actionUrl + directSource + id);
    }

    public add<T>(directSource: string, itemName: any): Observable<any> {
        const toAdd = JSON.stringify(itemName);
        return this.http.post<any>(this.actionUrl + directSource, toAdd);
    }

    public update<T>(directSource: string, id: number, itemToUpdate: any): Observable<T> {
        const toUpdate = JSON.stringify(itemToUpdate);
        return this.http.put<T>(this.actionUrl + directSource + id, toUpdate);
    }

    public delete<T>(directSource: string, id: number): Observable<T> {
        return this.http.delete<T>(this.actionUrl + directSource + id);
    }
}


@Injectable()
export class CustomInterceptor implements HttpInterceptor {

    constructor(private _cookieService: CookieService) { 

    }


    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!req.headers.has('Content-Type')) {
            if(this._cookieService.get('authorized') === 'true') {
                req = req.clone({ headers: req.headers.set('Content-Type', 'application/json').set('Authorization', 'Basic ' + 
                    this._cookieService.get('session-token')) });
            } else {
                req = req.clone({ headers: req.headers.set('Content-Type', 'application/json')});
            }
        }

        if(this._cookieService.get('authorized') === 'true') {
            req = req.clone({ headers: req.headers.set('Accept', 'application/json').set('Authorization', 'Basic ' + 
                this._cookieService.get('session-token')) });
        } else {
            req = req.clone({ headers: req.headers.set('Accept', 'application/json')});
        }

        return next.handle(req);
    }
}