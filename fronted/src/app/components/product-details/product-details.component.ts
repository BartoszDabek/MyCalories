import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { DataService } from '../../shared/services/data-service.service';
import { ProductInterface } from '../../shared/interfaces/product';
import { LoginService } from '../../shared/services/login-service.service';
import { Configuration } from '../../app.constants';
import {NgbRatingConfig} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  private endPoint: string = 'product/';
  product: ProductInterface;
  opinions: any;
  sub: any;
  opinionBody: string;
  currentRate: number;

  constructor(
    private dataService: DataService,
    private loginService: LoginService,
    private route: ActivatedRoute,
    private http: HttpClient
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

      this.http.get(Configuration.HOME_URL + this.endPoint + id + '/opinions')
        .subscribe(
        res => {
          this.opinions = res;
        },
        err => {
          console.log("error in getting opinions product-details component");
        });


    })
  }

  addOpinion() {
    this.dataService.add("opinion/", {
      description: this.opinionBody,
      rating: this.currentRate,
      product: this.product,
      user: JSON.parse(localStorage.getItem("currentUser"))
    })
      .subscribe(
      res => {
        this.opinions.push(res);
      },
      err => {
        console.log("Error occurd in addOpinion opinion.component");
      });
  }


  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
