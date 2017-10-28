import { Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/login-service.service';
import { Credentials } from '../../interfaces/credentials';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userName: string;
  password: string;

  constructor(private _loginService: LoginService) { }

  ngOnInit() {
  }

  public login() {
    let credentials = {
      username: this.userName,
      password: this.password
    }

    this._loginService.getAuthentication(credentials);
  }

}
