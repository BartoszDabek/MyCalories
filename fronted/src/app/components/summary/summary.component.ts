import { Component, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DataService } from '../../shared/services/data-service.service';
import { Configuration } from '../../app.constants';
import { DailyCalories } from '../../shared/interfaces/daily-calories'


@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  private endPoint: string = 'dailyCalories/date';
  model = {
    year: Configuration.DATE_NOW.getFullYear(), 
    month: Configuration.DATE_NOW.getMonth() + 1, 
    day: Configuration.DATE_NOW.getDate()
  };
  dailyCalories: DailyCalories;


  constructor(private dataService: DataService) { }

  ngOnInit() {
    let params = new HttpParams().set("date", this.selectedDateToParam());
    this.dataService.getAll(this.endPoint, params)
      .subscribe(
        res => {
          this.dailyCalories = res;
        },
        err => {
          console.log("error in getAll summary component");
        }
      );
  }

  summary() {
    let params = new HttpParams().set("date", this.selectedDateToParam());
    this.dataService.getAll(this.endPoint, params)
      .subscribe(
        res => {
          this.dailyCalories = res;
          console.log(res);
        },
        err => {
          this.dailyCalories = undefined;
          console.log("error in getAll summary component");
        }
      );
  }

  test(object: any) {
    console.log(object);
  }

  newProduct() {
    console.log("nowy produkt");
  }

  private returnType(defaultType: string, realValue: number, expectedValue: number): string {
    if(realValue > 1200) {
      return "danger"
    }
    return defaultType;
  }

  private dayNoExist(): boolean {
    if (this.dailyCalories == null) {
      this.setDefaultValues();
    }
    return true;
  }

  private setDefaultValues() {
    this.dailyCalories = {
      'nutritionalValues' : {
      'calories' : 0,
      'proteins' : 0,
      'fats' : 0,
      'carbs' : 0
    }};
  }

  private selectedDateToParam(): string {
    let day = this.appendZeroToNumber(this.model.day);
    let month = this.appendZeroToNumber(this.model.month);
    let year = this.appendZeroToNumber(this.model.year);

    // return year + "-" + month + "-" + day;
    return "2017-11-06";
  }

  private appendZeroToNumber(model: any): string {
    return model < 10 ? ("0" + model) : model;
  }

}
