import {HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpParams, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';


@Injectable()
export class DataService {

  constructor(private http: HttpClient) {
  }

  public getAll<T>(directSource: string, parameters?: HttpParams, responseType = 'json'): Observable<T> {
    // change responseType when it will work
    // https://github.com/angular/angular/issues/18586
    return this.http.get<T>(directSource, {params: parameters, responseType: responseType as 'json'});
  }

  public getSingle<T>(directSource: string, id: number): Observable<T> {
    return this.http.get<T>(directSource + id);
  }

  public add<T>(directSource: string, itemName: any): Observable<any> {
    const toAdd = JSON.stringify(itemName);
    return this.http.post<any>(directSource, toAdd);
  }

  public update<T>(directSource: string, id: number, itemToUpdate: any): Observable<T> {
    const toUpdate = JSON.stringify(itemToUpdate);
    return this.http.put<T>(directSource + id, toUpdate);
  }

  public delete<T>(directSource: string, id: number): Observable<T> {
    return this.http.delete<T>(directSource + id);
  }
}


@Injectable()
export class CustomInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let credentials = (localStorage.getItem("credentials"));

    if (!req.headers.has('Content-Type')) {
      req = this.handleRequest('Content-Type', req);
    }

    req = this.handleRequest('Accept', req);

    return next.handle(req);
  }

  private handleRequest(key: string, req: HttpRequest<any>) {
    let credentials = (localStorage.getItem("credentials"));

    if (credentials !== null) {
      return req.clone({
        headers: req.headers.set(key, 'application/json').set('Authorization', 'Basic ' + credentials)
      });
    } else {
      return req.clone({headers: req.headers.set(key, 'application/json')});
    }
  }

}
