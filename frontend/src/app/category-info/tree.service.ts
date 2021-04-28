import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {User} from '../model/Tree';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  urlPrefix = '/api/users';

  constructor(private httpClient: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.httpClient.get(this.urlPrefix + '/') as Observable<User[]>;
  }

  getCategoryLabels(): Observable<string[]> {
    return this.httpClient.get(this.urlPrefix + '/').pipe(
      map(response => this.getName(response as any))
    )
  }

  private getName(response) {
    return response.map(category => category.name);
  }

  getCategoryColors(): Observable<string[]> {
    return this.httpClient.get(this.urlPrefix + '/').pipe(
      map(response => this.getColor(response as any))
    )
  }

  private getColor(response) {
    return response.map(category => category.color);
  }

  public getCategoryIcon(categoryName: string):string{
    return 'https://localhost:8443'+this.urlPrefix+'/image?categoryName='+categoryName;
  }
}
