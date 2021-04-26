import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {Plan} from './explore';


@Injectable({
  providedIn: 'root'
})
export class PlanService {

  urlPrefix: string = "/api/plans/";

  constructor(private httpClient: HttpClient) { }

  getPage(page:number):Observable<Plan[]> {
    return this.httpClient.get(this.urlPrefix ) as Observable<Plan[]>
  }
}
