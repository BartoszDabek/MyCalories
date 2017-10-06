import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ProductService } from '../../services/product.service';
import { ProductInterface } from '../../interfaces/product';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  private product: ProductInterface;
  private sub:any;

  constructor(private productService: ProductService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = params['id'];

      this.productService.findById(id)
      .subscribe((response) => {
        this.product = response;
      })
    })
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
