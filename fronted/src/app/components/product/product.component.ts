import {debounceTime, map} from 'rxjs/operators';
import {Component, OnInit} from '@angular/core';
import {ProductInterface} from '../../shared/interfaces/product';
import {DataService} from '../../shared/services/data-service.service'
import {Observable} from 'rxjs';
import {LoginService} from '../../shared/services/login-service.service'


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  apiEndPoint: string = 'product/';
  products: ProductInterface[];
  reverseSort: boolean;
  sortName: string;
  term: string;

  constructor(private dataService: DataService, public loginService: LoginService) {
  }

  ngOnInit() {
    this.dataService.getAll<ProductInterface[]>(this.apiEndPoint)
      .subscribe(
        res => {
          this.products = res;
          for (let i = 0; i < res.length; i++) {
            this.products[i].name = this.capitalizeFirstLetterAndLowerCaseOthers(this.products[i].name);
            this.products[i].category.name = this.capitalizeFirstLetterAndLowerCaseOthers(this.products[i].category.name);
          }
        },
        err => {
          console.log("error in get_all product component");
        });
  }

  private capitalizeFirstLetterAndLowerCaseOthers(name: string) {
    return name = name.charAt(0).toUpperCase() + name.slice(1).toLowerCase();
  }

  setSortBy(name: string) {
    this.sortName = name;
  }

  reverseSorting() {
    this.reverseSort = !this.reverseSort;
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      map(term => term.length < 2 ? []
        : this.products.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)),);

  formatter = (x: { name: string }) => x.name;

}
