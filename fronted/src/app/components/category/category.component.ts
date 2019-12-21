import { Component, OnInit } from '@angular/core';
import { CategoryInterface } from '../../shared/interfaces/category';
import { DataService } from '../../shared/services/data-service.service';
import { LoginService } from '../../shared/services/login-service.service'

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  apiEndPoint: string = 'category/';
  categoryNameInput: string;
  categories: CategoryInterface[];

  constructor(private dataService: DataService, public loginService: LoginService) { }

  ngOnInit() {
    this.dataService.getAll<CategoryInterface[]>(this.apiEndPoint)
      .subscribe(
        res => {
          this.categories = res;
        },
        err => {
            console.log("error in get_all category component");
        });
  }

  private addCategory() {
    if (this.categoryIsFilled()) {
        this.dataService.add<CategoryInterface>(this.apiEndPoint, {
          name: this.categoryNameInput
        })
        .subscribe(
          res => {
            this.categoryNameInput = "";
            this.categories.push(res);
          },
          err => {
            console.log("Error occurd in add_category category.component");
          });
    }
  }


  deleteCategory(category: CategoryInterface) {
    for (let i = 0; i < this.categories.length; i++) {
      if (this.categories[i] === category) {
        this.dataService.delete<CategoryInterface>(this.apiEndPoint, category.id)
          .subscribe(
            res => {
              this.categories.splice(i, 1);
            },
            err => {
              console.log("Error occurd in delete_category category.component");
            });
      }
    }
  }

  private categoryIsFilled() {
    if(this.categoryNameInput !== undefined) {
      this.categoryNameInput = this.categoryNameInput.trim();
      return this.categoryNameInput !== "";
    }
    return false;
  }

}
