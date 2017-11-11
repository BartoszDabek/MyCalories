import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Meal } from '../../../shared/interfaces/meal'
import { DailyCalories } from '../../../shared/interfaces/daily-calories'
import { ProductInterface } from '../../../shared/interfaces/product'
import { DataService } from '../../../shared/services/data-service.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-meals-details',
  templateUrl: './meals-details.component.html',
  styleUrls: ['./meals-details.component.css']
})
export class MealsDetailsComponent implements OnInit {

  private edit: boolean;
  private editProductMeal: boolean;
  private mealCopy: Meal;
  private products: ProductInterface[];
  private product: ProductInterface;

  constructor(private dataService: DataService) { }

  @Input() meal: Meal;
  @Input() dailyCalories: DailyCalories;
  @Output() dailyCaloriesChange: EventEmitter<DailyCalories> = new EventEmitter<DailyCalories>();

  ngOnInit() {
  }

  deleteProduct(object: any) {
    for (let i of this.dailyCalories.meals) {
      i.productMeals = i.productMeals.filter(item => item !== object);
    }

    this.dailyCaloriesChange.emit(this.dailyCalories);
  }

  editProduct() {
    this.editProductMeal = true;
    this.mealCopy = JSON.parse(JSON.stringify(this.meal));
  }

  cancelEdit() {
    this.meal = this.mealCopy;
    this.editProductMeal = false;
  }

  saveProduct(object: any) {
    for (let i of this.dailyCalories.meals) {
      if (i.id === object.id) {
        i = object;
      }
    }

    this.dailyCaloriesChange.emit(this.dailyCalories);
    this.editProductMeal = false;
  }

  getSum(object: any) {
    let sum = 0;
    for (let pm of this.meal.productMeals) {
      sum += pm.product.nutritionalValues[object] * pm.amount;
    }

    this.meal.nutritionalValues[object] = sum;
    return sum;
  }

  deleteMeal(object: any) {
    this.dailyCalories.meals = this.dailyCalories.meals.filter(item => item !== object);
    this.dailyCaloriesChange.emit(this.dailyCalories);
  }

  open(object: any) {
    this.edit = !this.edit;
    this.dataService.getAll<ProductInterface[]>("product/")
      .subscribe(
      res => {
        this.products = res;
      },
      err => {
        //
      });
  }

  addProductToMeal() {
    this.editProduct();
    this.meal.productMeals.push({
      id: 0,
      amount: 1,
      product: this.product
    });

    this.product = undefined;
  }

  search = (text$: Observable<string>) =>
    text$
      .debounceTime(200)
      .map(product => product.length < 2 ? []
        : this.products.filter(v => v.name.toLowerCase().indexOf(product.toLowerCase()) > -1).slice(0, 10));

  formatter = (x: { name: string }) => x.name;

}
