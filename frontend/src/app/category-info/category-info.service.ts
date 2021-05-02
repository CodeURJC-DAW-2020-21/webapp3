import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Category } from '../model/Category';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CategoryInfoService {

  urlPrefix: string = "/api/categories";

  constructor(private httpClient: HttpClient) { }

  getCategory():Observable<Category> {
    return this.httpClient.get(this.urlPrefix + '/?name') as Observable<Category>
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
