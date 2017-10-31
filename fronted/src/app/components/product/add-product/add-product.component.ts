import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { DataService } from '../../../services/data-service.service';
import { CategoryInterface } from '../../../interfaces/category';
import { ProductInterface } from '../../../interfaces/product';


@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  apiEndPoint = "product/"
  categories: CategoryInterface[];
  model: any = {};
  productForm: FormGroup

  constructor(private _dataService: DataService) { }

  ngOnInit() {
    this._dataService.getAll<CategoryInterface[]>("category/")
      .subscribe((res) => {
        this.categories = res;
      });

    this.productForm = new FormGroup({
      'name': new FormControl(this.model.name, [
        Validators.required
      ]),
      'calories': new FormControl(this.model.calories, [
        Validators.required,
        Validators.min(0)
      ]),
      'proteins': new FormControl(this.model.proteins, [
        Validators.required,
        Validators.min(0)
      ]),
      'fats': new FormControl(this.model.fats, [
        Validators.required,
        Validators.min(0)
      ]),
      'carbs': new FormControl(this.model.carbs, [
        Validators.required,
        Validators.min(0)
      ]),
      'category': new FormControl(this.model.category, [
        Validators.required
      ])
    });
  }

  createProduct() {
    let nutritionalValues = {
      calories: this.calories.value,
      proteins: this.proteins.value,
      fats: this.fats.value,
      carbs: this.carbs.value
    }

    this._dataService.add<ProductInterface>(this.apiEndPoint, {
      name: this.name.value,
      category: this.category.value,
      nutritionalValues: nutritionalValues
    })
      .subscribe(
      res => {
        console.log("Dodałem produkt");
      },
      err => {
        console.log("Error occurd in add_category category.component");
      });
  }

  get name() { return this.productForm.get('name'); }

  get calories() { return this.productForm.get('calories'); }
  
  get proteins() { return this.productForm.get('proteins'); }

  get fats() { return this.productForm.get('fats'); }

  get carbs() { return this.productForm.get('carbs'); }

  get category() { return this.productForm.get('category'); }

}
