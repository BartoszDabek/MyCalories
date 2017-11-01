import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataService } from '../../services/data-service.service';
import { ProductInterface } from '../../interfaces/product';
import { LoginService } from '../../services/login-service.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  private endPoint: string = 'product/';
  product: ProductInterface;
  sub:any;
  opinionBody: string;

  constructor(
    private dataService: DataService, 
    private route: ActivatedRoute,
    private loginService: LoginService
  ) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = params['id'];
      this.dataService.getSingle<ProductInterface>(this.endPoint, id)
        .subscribe(
          res => {
            this.product = res;
          },
          err => {
              console.log("error in get_single product-details component");
          });
    })
  }

  addOpinion(){
    console.log(JSON.parse(localStorage.getItem("currentUser")));
    this.dataService.add("opinion/", {
      description: this.opinionBody,
      product: this.product,
      user: JSON.parse(localStorage.getItem("currentUser"))
    })
      .subscribe(
      res => {
        console.log("Dodałem produkt");
        console.log(res);
      },
      err => {
        console.log(err);
        console.log("Error occurd in add_category category.component");
      });
  }


  ngOnDestroy() {
    this.sub.unsubscribe();
  }
  
}
