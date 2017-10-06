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
  sortName: string;
  reverseSort: boolean;
  product: ProductInterface;

  constructor(private productService: ProductService ) { }

  ngOnInit() {
    this.productService.getProducts()
      .subscribe((response) => {
        this.products = response;
        for(let i=0; i<response.length; i++) {
          this.products[i].name = this.capitalizeFirstLetterAndLowerCaseOthers(this.products[i].name);
          this.products[i].category.name = this.capitalizeFirstLetterAndLowerCaseOthers(this.products[i].category.name);
        }
      });
  }

  capitalizeFirstLetterAndLowerCaseOthers(name: string) {
    return name = name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
  }

  setSortBy(name: string) {
    this.sortName = name;
  }

  reverseSorting() {
    this.reverseSort = !this.reverseSort;
  }

  search = (text$: Observable<string>) =>
  text$
    .debounceTime(200)
    .map(term => term.length < 2 ? []
      : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));

  formatter = (x: {name: string}) => x.name;

}