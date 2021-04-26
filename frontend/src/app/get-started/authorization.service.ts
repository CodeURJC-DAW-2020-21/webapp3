import { Injectable } from '@angular/core';
import { User } from "../model/User";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

const BASE_URL = '/api/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  constructor(private httpClient : HttpClient, private router: Router) {
    this.reqIsLogged()
  }

  logged: boolean
  user: User;

  reqIsLogged() {

    this.httpClient.get('/api/users/me', { withCredentials: true }).subscribe(
      response => {
        this.user = response as User;
        this.logged = true;
      },
      error => {
        if (error.status != 404) {
          console.error('Error when asking if logged: ' + JSON.stringify(error));
        }
      }
    );
  }

  login(email:string, password:string){
      return this.httpClient.post(BASE_URL + "/login" , { username : email, password : password}, {withCredentials:true})
  }

  logOut() {

    return this.httpClient.post(BASE_URL + '/logout', { withCredentials: true })
      .subscribe(_ => {
        console.log("LOGOUT: Successfully");
        this.logged = false;
        this.user = undefined;
        this.router.navigate([''])
      });

  }

  isLogged() {
    return this.logged;
  }

  isAdmin() {
    return this.user && this.user.roles.indexOf('ADMIN') !== -1;
  }

  currentUser() {
    return this.user;
  }

  createUser(newUser: User) {
    return this.httpClient.post("/api/users", newUser)
  }
}
