import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {pageable, Plan} from './explore';


@Injectable({
  providedIn: 'root'
})
export class PlanService {

  urlPrefix: string = "/api/plans/";

  constructor(private httpClient: HttpClient) { }

  getPage(page:number):Observable<pageable> {
    return this.httpClient.get(this.urlPrefix+"explore?page="+page.toString()) as Observable<pageable>;
  }
  completePlan(planName:string):Observable<Plan>{
    return this.httpClient.post(this.urlPrefix +"done?planName="+ planName,"",{withCredentials:true} ) as Observable<Plan>
  }
  likePlan(name:string):Observable<Plan>{
    return this.httpClient.put(this.urlPrefix +"favN?planName="+name,"",{withCredentials:true} ) as Observable<Plan>
  }
  dislikePlan(name:string):Observable<Plan>{
    return this.httpClient.put(this.urlPrefix +"notFavN?planName="+name,"",{withCredentials:true} ) as Observable<Plan>
  }
}
