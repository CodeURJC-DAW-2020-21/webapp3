import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Category } from '../model/Category';



@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  urlPrefix: string = "/api/categories";

  constructor(private httpClient: HttpClient) { }

  getCategories():Observable<Category[]> {
    return this.httpClient.get(this.urlPrefix + '/') as Observable<Category[]>
  }


  public getCategoryIcon(categoryName: string):string{
    return 'https://localhost:8443'+this.urlPrefix+'/image?categoryName='+categoryName;
  }

  createCategory(newCat: Category) {
    return this.httpClient.post(this.urlPrefix, newCat);
  }

  getCategory(name: string): Observable<Category> {
    return this.httpClient.get(this.urlPrefix+"/"+name, {withCredentials:true}) as Observable<Category>
  }

  getCategoryNotRegistered(name:string): Observable<Category> {
    return this.httpClient.get(this.urlPrefix+'/'+name) as Observable<Category>
  }
}
