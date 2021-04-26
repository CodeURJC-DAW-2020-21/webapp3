import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {Category} from './Category';


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
}
