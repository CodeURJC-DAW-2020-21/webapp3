import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";

const urlPrefix = 'api/users'

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private httpClient: HttpClient) { }

  getUserInfo(): Observable<User>{
    return this.httpClient.get(urlPrefix + '/profile', {withCredentials:true}) as Observable<User>
  }

  editProfile(username: string, name: string, surname: string, encodedPassword: string, confirmEncodedPassword: string): Observable<User> {
    let formData = {
      username: username,
      name: name,
      surname: surname,
      encodedPassword: encodedPassword,
      confirmEncodedPassword: confirmEncodedPassword
    }
    return this.httpClient.put(urlPrefix + '/profile', formData , {withCredentials: true}) as Observable<User>
  }
}
