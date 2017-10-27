import { Component, OnInit } from '@angular/core';
import { DataService } from '../../../services/data-service.service';
import { CategoryInterface } from '../../../interfaces/category';
import { ProductInterface } from '../../../interfaces/product';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  private apiEndPoint = "product/"
  private categories;

  private nameInput;
  private caloriesInput;
  private proteinsInput;
  private fatsInput;
  private carbsInput;
  private categoryInput;

  constructor(private _dataService: DataService) { }

  ngOnInit() {
    this._dataService.getAll<CategoryInterface[]>("category/")
    .subscribe((res) => {
      this.categories = res;
      this.categoryInput = this.categories[0];
    });


  }

  createProduct() {
    let nutritionalValues = {
      calories: this.caloriesInput,
      proteins: this.proteinsInput,
      fats: this.fatsInput,
      carbs: this.carbsInput
    }

    this._dataService.add<ProductInterface>(this.apiEndPoint, { 
      name: this.nameInput,
      category: this.categoryInput,
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

}
