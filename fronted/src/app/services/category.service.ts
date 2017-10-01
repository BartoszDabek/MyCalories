import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';


@Injectable()

export class CategoryService {

  constructor(public http: Http) {
    console.log('Category service connected...');
  }

  getCategories() {
      return this.http.get('http://localhost:8080/category')
        .map(res => res.json());
  }

  addCategory(categoryName: string) {
    let category = {
      name: categoryName
    }
    return this.http.post('http://localhost:8080/category', category)
      .map(res => res.json());
  }

  deleteCategory(id: number) {
    return this.http.delete('http://localhost:8080/category/' + id);
}

  
}