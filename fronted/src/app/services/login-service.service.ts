import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Configuration } from '../app.constants';
import { Credentials } from '../interfaces/credentials'

@Injectable()
export class LoginService {

  public username;
  public password;
  public authenticated = false;
  private actionUrl: string;
  
  constructor(private http: HttpClient) {
      this.actionUrl = Configuration.HOME_URL;
  }

  public getAuthentication(credentials: Credentials) {

    return this.http.get(this.actionUrl + "/user")
      .subscribe(
        res => {
          console.log("login-service success");
          this.authenticated = true;
          this.username = credentials.username;
          this.password = credentials.password;
        },
        err => {
          console.log("login-service error");
        }
      );
}

public setLoggedOut() {
  this.authenticated = false;
  this.username = "";
  this.password = "";
};

public setLoggedIn() {
  this.authenticated = true;
};

public isLoggedIn() {
  return this.authenticated;
};

// $rootScope.hasAuthority = function (role) {
//   if ($rootScope.authenticated === false) {
//       return false;
//   }
//   var i;
//   for (i = 0; i < $rootScope.currentUser.roles.length; i++) {
//       if ($rootScope.currentUser.roles[i].name === role) {
//           return true;
//       }
//   }
//   return false;
// };

}
