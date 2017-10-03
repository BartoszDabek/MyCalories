import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/map';

import { ProductService } from '../../services/product.service';
import { ProductInterface } from '../../interfaces/product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  products: ProductInterface[];
  
  constructor(private productService: ProductService ) {
    
  }

  search = (text$: Observable<string>) =>
  text$
    .debounceTime(200)
    .map(term => term.length < 2 ? []
      : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));

formatter = (x: {name: string}) => x.name;

  
  ngOnInit() {
    this.productService.getProducts()
      .subscribe((response) => {
        this.products = response;
      });
  }

}