import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ProductService {

  constructor(public http: Http) { }

  getProducts() {
    return this.http.get('http://localhost:8080/product')
      .map(res => res.json());
  }
}
