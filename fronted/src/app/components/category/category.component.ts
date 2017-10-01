import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  categories: Category[];
  private category: string;

  constructor(private categoryService: CategoryService) {
    console.log('constructor of category component');
  }

  ngOnInit() {
    console.log('ngInit of category component');
    this.categoryService.getCategories().subscribe((categories) => {
      this.categories = categories;
    });
  }

  addCategory() {
    if (this.category !== undefined) {
      this.categoryService.addCategory(this.category).subscribe(
        res => {
          this.category = "";
          this.categories.push(res);
          console.log(res);
        },
        err => {
          console.log("Error occured");
        });
    }
  }

  deleteCategory(category: Category) {
    for (let i = 0; i < this.categories.length; i++) {
      if (this.categories[i] == category) {
        this.categoryService.deleteCategory(category.id)
          .subscribe(
          res => {
            this.categories.splice(i, 1);
            console.log(res);
          },
          err => {
            console.log("Error occured");
          }
          );
      }
    }
  }

}

interface Category {
  id: number,
  name: string
}