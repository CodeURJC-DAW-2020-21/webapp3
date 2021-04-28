import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {User} from '../model/User';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  urlPrefix = '/api/users';

  constructor(private httpClient: HttpClient) { }

  getUser(): Observable<User[]> {
    return this.httpClient.get(this.urlPrefix + '/') as Observable<User[]>;
  }

  getCategoryLabels(): Observable<string[]> {
    return this.httpClient.get(this.urlPrefix + '/').pipe(
      map(response => this.getName(response as any))
    )
  }

  private getName(response) {
    return response.map(user => user.name);
  }

  private getRol(response){
    return response.map(user => user.roles);
  }

  public getCategoryIcon(categoryName: string):string{
    return 'https://localhost:8443'+this.urlPrefix+'/image?categoryName='+categoryName;
  }
}
