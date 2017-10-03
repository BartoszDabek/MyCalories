import { Component, OnInit } from '@angular/core';

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
  
  ngOnInit() {
    this.productService.getProducts()
      .subscribe((response) => {
        this.products = response;
      });
  }

}