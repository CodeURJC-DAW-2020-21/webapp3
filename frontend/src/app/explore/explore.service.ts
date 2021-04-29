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
  completePlan(planName:string):Observable<Plan>{
    return this.httpClient.post(this.urlPrefix +"done?planName="+ planName,"",{withCredentials:true} ) as Observable<Plan>
  }
  likePlan(name:string):Observable<Plan>{
    return this.httpClient.put(this.urlPrefix +"favN?planName="+name,"",{withCredentials:true} ) as Observable<Plan>
  }
  dislikePlan(abbv:string):Observable<Plan>{
    return this.httpClient.put(this.urlPrefix +"notfavA?abbrev="+abbv,"",{withCredentials:true} ) as Observable<Plan>
  }
}
