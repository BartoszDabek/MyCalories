import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DataService } from '../../services/data-service.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  model: any = {};
  apiEndPoint = '/user';

  constructor(private router: Router, private dataService: DataService) { }

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

}
