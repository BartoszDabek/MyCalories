import { Component, OnInit } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DataService } from '../../shared/services/data-service.service';
import { Configuration } from '../../app.constants';
import { DailyCalories } from '../../shared/interfaces/daily-calories';


@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  private endPoint: string = 'dailyCalories/';
  private downloadCSVAPI: string = 'downloadCSV';
  private getEndPoint: string = 'dailyCalories/date';
  model = {
    year: Configuration.DATE_NOW.getFullYear(),
    month: Configuration.DATE_NOW.getMonth() + 1,
    day: Configuration.DATE_NOW.getDate()
  };
  dailyCalories: DailyCalories;

  constructor(private dataService: DataService) { }

  ngOnInit() {
    this.getSelectedDay();
  }

  summary() {
    this.getSelectedDay();
  }

  getCSV() {
    let params = new HttpParams().set("date", this.selectedDateToParam());
    this.dataService.getAll<DailyCalories>(this.downloadCSVAPI, params, 'text')
    .subscribe(
    res => {
      this.downloadFile(res);
    },
    err => {
      console.log("error while downloading file");
    });
}

  downloadFile(data: any) {
    let parsedResponse = data;
    let fileName = "Daily-report-" + parsedResponse.substring(0, parsedResponse.indexOf(',')) + ".csv";
    let blob = new Blob([parsedResponse], { type: 'text/csv' });
    let url = window.URL.createObjectURL(blob);
    if(navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, fileName);
    } else {
        let a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }
    window.URL.revokeObjectURL(url);
  }

  addMeal() {
    if (this.dayNoDefined()) {
      this.createNewDay();
    } else {
      this.pushToMeals();
    }
  }

  update(item) {
    this.dataService.update<DailyCalories>(this.endPoint, this.dailyCalories.id, item)
      .subscribe(
      res => {
        this.dailyCalories = res;
      },
      err => {
        console.log(err);
      });
  }



  private getSelectedDay() {
    let params = new HttpParams().set("date", this.selectedDateToParam());
    this.dataService.getAll(this.getEndPoint, params)
      .subscribe(
      res => {
        this.dailyCalories = res;
      },
      err => {
        this.dailyCalories = undefined;
      });
  }

  private dayNoDefined(): boolean {
    return this.dailyCalories.id === undefined ? true : false;
  }

  private createNewDay() {
    this.dataService.add(this.endPoint, {
      day: {
        date: this.selectedDateToParam()
      }
    })
      .subscribe(
      res => {
        this.dailyCalories = res;
        this.pushToMeals();
      },
      err => {
        console.log(err);
      });
  }

  private pushToMeals() {
    this.dailyCalories.meals.unshift({
      id: 0,
      name: undefined,
      nutritionalValues: {
        calories: 0,
        proteins: 0,
        fats: 0,
        carbs: 0
      },
      productMeals: []
    });
  }


  private returnColor(defaultColor: string, realValue: number, expectedValue: number): string {
    if (realValue > expectedValue) {
      return "danger"
    }
    return defaultColor;
  }

  dayNoExist(): boolean {
    if (this.dailyCalories === undefined) {
      this.setDefaultValues();
    }
    return true;
  }

  private setDefaultValues() {
    this.dailyCalories = {
      nutritionalValues: {
        calories: 0,
        proteins: 0,
        fats: 0,
        carbs: 0
      },
      id: undefined
    };
  }

  private selectedDateToParam(): string {
    let day = this.appendZeroToNumber(this.model.day);
    let month = this.appendZeroToNumber(this.model.month);
    let year = this.appendZeroToNumber(this.model.year);

    return year + "-" + month + "-" + day;
  }

  private appendZeroToNumber(model: any): string {
    return model < 10 ? ("0" + model) : model;
  }

}
