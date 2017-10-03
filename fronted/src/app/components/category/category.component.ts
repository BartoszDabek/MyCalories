import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../services/category.service';
import { CategoryInterface } from '../../interfaces/category';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  private categories: CategoryInterface[];
  private category: string;

  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getCategories().subscribe((categories) => {
      this.categories = categories;
    });
  }

  addCategory() {
    console.log('kategoria = ' + this.category);
    if (this.categoryIsFilled()) {
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

  
  deleteCategory(category: CategoryInterface) {
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
  
  private categoryIsFilled() {
    return this.category !== undefined && this.category !== "";
  }

}