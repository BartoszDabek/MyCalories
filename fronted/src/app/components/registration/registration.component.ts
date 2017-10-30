import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms'
import { DataService } from '../../services/data-service.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  model: any = {};
  apiEndPoint = '/user';


  constructor(private router: Router, private dataService: DataService) { }

  ngOnInit() {

  }

  register() {
    this.dataService.add(this.apiEndPoint, this.model)
      .subscribe(
        data => {
          this.router.navigateByUrl('/login');
        },
        err => {
          console.log("error in registration component register");
        }
      )
  }

  isEqual(first: string, second: string) {
    return first === second;
  }

  get username() { return this.model.get('username'); }

}
