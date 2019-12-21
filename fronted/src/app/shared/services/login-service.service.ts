import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Credentials} from '../interfaces/credentials'
import {Router} from '@angular/router';


@Injectable()
export class LoginService {

  public authenticated: boolean;

  constructor(private http: HttpClient, private router: Router) {
  }

  public getAuthentication(credentials: Credentials) {
    const headers = new HttpHeaders()
      .set('Authorization', 'Basic ' + btoa(credentials.username + ":" + credentials.password))

    return this.http.get("user", {headers: headers})
      .subscribe(
        res => {
          console.log(res);
          localStorage.setItem("credentials", btoa(credentials.username + ":" + credentials.password));
          localStorage.setItem('currentUser', JSON.stringify(res));
          this.authenticated = true;
          this.router.navigateByUrl('home');
        },
        err => {
          console.log("login-service error");
          console.log(err)
        }
      );
  }

  public getCurrentUsername(): string {
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (currentUser == null) {
      return "";
    }
    return currentUser.username;
  }

  public setLoggedOut() {
    localStorage.removeItem("credentials");
    localStorage.removeItem("currentUser");
    this.authenticated = false;
  };

  public setLoggedIn() {
    this.authenticated = true;
  };

  public isLoggedIn(): boolean {
    if (localStorage.getItem("credentials") !== null) {
      this.authenticated = true;
    }
    return this.authenticated;
  };

  public hasRole(roleName: string): boolean {
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (currentUser == null || currentUser.roles.find(x => x.roleName === roleName) == null) {
      return false;
    }
    return true;
  }

}
