import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Category } from '../model/Category';

class CategoryEdition {
  description: string;
  color: string;
}

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

  editCategory(data: CategoryEdition, categoryName: string): Observable<Category> {
    return this.httpClient.put("/api/categories?categoryName="+categoryName, data, {withCredentials: true}) as Observable<Category>
  }

  getCategory(name: string): Observable<Category> {
    return this.httpClient.get(this.urlPrefix+"/"+name, {withCredentials:true}) as Observable<Category>
  }

  getCategoryNotRegistered(name:string): Observable<Category> {
    return this.httpClient.get(this.urlPrefix+'/'+name) as Observable<Category>
  }

  likeCategory(name:string): Observable<Category> {
    return this.httpClient.put(this.urlPrefix+"/fav?categoryName="+name ,{withCredentials: true}) as Observable<Category>
  }

  dislikeCategory(name:string): Observable<Category> {
    return this.httpClient.put(this.urlPrefix+"/notFav?categoryName="+name, {withCredentials: true}) as Observable<Category>
  }

}
