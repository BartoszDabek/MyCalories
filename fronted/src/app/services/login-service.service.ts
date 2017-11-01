import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Configuration } from '../app.constants';
import { Credentials } from '../interfaces/credentials'
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class LoginService {

  username: string;
  password: string;
  actionUrl: string;
  authenticated: boolean;
  currentUser: any;
  
  constructor(private http: HttpClient, private _router: Router, private cookieService: CookieService) {
      this.actionUrl = Configuration.HOME_URL;
  }

  public getAuthentication(credentials: Credentials) {
    const headers = new HttpHeaders()
      .set('Authorization', 'Basic ' + btoa(credentials.username + ":" + credentials.password))

    return this.http.get(this.actionUrl + "/user", { headers: headers })
      .subscribe(
        res => {
          console.log("login-service success");
          this.currentUser = res;
          this.cookieService.set( 'username', credentials.username );
          this.cookieService.set( 'authorized', 'true' );
          this.cookieService.set( 'session-token', btoa(credentials.username + ":" + credentials.password ));
          this.authenticated = true;
          this.username = credentials.username;
          this.password = credentials.password;
          this._router.navigateByUrl('');
        },
        err => {
          console.log("login-service error");
        }        
      );
}

public setLoggedOut() {
  this.cookieService.deleteAll();
  this.authenticated = false;
  this.username = undefined;
  this.password = undefined;
};

public setLoggedIn() {
  this.authenticated = true;
};

public isLoggedIn() {
  if(this.cookieService.get('authorized') === 'true') {
    this.authenticated = true;
  }
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
