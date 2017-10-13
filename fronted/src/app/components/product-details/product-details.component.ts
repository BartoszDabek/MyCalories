import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataService } from '../../services/data-service.service'
import { ProductInterface } from '../../interfaces/product';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  private apiEndPoint: string = 'product/';
  private product: ProductInterface;
  private sub:any;

  constructor(private _dataService: DataService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = params['id'];

      this._dataService.getSingle<ProductInterface>(this.apiEndPoint, id)
        .subscribe(
          res => {
            this.product = res;
          },
          err => {
              console.log("error in get_single product-details component");
          });
    })
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
