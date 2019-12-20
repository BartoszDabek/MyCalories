import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Configuration} from '../../app.constants';
import {Credentials} from '../interfaces/credentials'
import {Router} from '@angular/router';


@Injectable()
export class LoginService {

  public authenticated: boolean;
  private actionUrl: string;

  constructor(private http: HttpClient, private router: Router) {
    this.actionUrl = Configuration.HOME_URL + "user";
  }

  public getAuthentication(credentials: Credentials) {
    const headers = new HttpHeaders()
      .set('Authorization', 'Basic ' + btoa(credentials.username + ":" + credentials.password))

    return this.http.get(this.actionUrl, {headers: headers})
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
