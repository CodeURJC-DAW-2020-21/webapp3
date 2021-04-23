import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {Category} from './Category';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  urlPrefix: string = "/api/categories";

  constructor(private httpClient: HttpClient) { }

  getCategories():Observable<Category[]> {
    return this.httpClient.get(this.urlPrefix + '/') as Observable<Category[]>
  }

  getIcon(categoryName:string): Observable<Blob>{
    return this.httpClient.get(this.urlPrefix + "/image?categoryName=" + categoryName , {responseType:'blob'});
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

}
