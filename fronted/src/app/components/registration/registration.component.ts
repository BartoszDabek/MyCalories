import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AbstractControl, FormControl, FormGroup, Validators, ValidatorFn } from '@angular/forms'
import { DataService } from '../../shared/services/data-service.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {

  model: any = {};
  apiEndPoint = 'user';
  registerForm: FormGroup

  constructor(private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.registerForm = new FormGroup({
      'username': new FormControl(this.model.username, [
        Validators.required,
        Validators.minLength(4)
      ]),
      'passwords': new FormGroup({
        'password': new FormControl(this.model.password, [
          Validators.required,
          Validators.minLength(6)
        ]),
        'confirm_password': new FormControl(this.model.confirm_password, [
          Validators.required,
          Validators.minLength(6)
        ]),
      }, passwordConfirming),
      'email': new FormControl(this.model.email, [
        Validators.required,
        Validators.email
      ])
    });
  }

  register() {
    this.model = {
      username: this.username.value,
      password: this.password.value,
      email: this.email.value
    };

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

  get username() { return this.registerForm.get('username'); }

  get passwords() { return this.registerForm.get('passwords'); }

  get password() { return this.registerForm.get('passwords').get('password'); }

  get confirm_password() { return this.registerForm.get('passwords').get('confirm_password'); }

  get email() { return this.registerForm.get('email'); }

}

export function passwordConfirming(c: AbstractControl): ValidatorFn {
  if (c.get('password').value === c.get('confirm_password').value) {
    return null;
  }
}
