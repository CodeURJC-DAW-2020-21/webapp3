import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { pageable, Plan } from './explore';

class PlanEdition {
  newDescription: string;
  abv: string;
  difficulty: number;
}

@Injectable({
  providedIn: 'root'
})
export class PlanService {

  urlPrefix: string = "/api/plans";

  constructor(private httpClient: HttpClient) { }

  getPage(page:number):Observable<pageable> {
    return this.httpClient.get(this.urlPrefix+"/explore?page="+page.toString()) as Observable<pageable>;
  }

  getPageByCat(category:string):Observable<Plan[]> {
    return this.httpClient.get("api/categories?name="+category) as Observable<Plan[]>;
  }
  completePlan(planName:string):Observable<Plan>{
    return this.httpClient.post(this.urlPrefix +"/done?planName="+ planName,"",{withCredentials:true} ) as Observable<Plan>
  }

  likePlan(name:string):Observable<Plan>{
    return this.httpClient.put(this.urlPrefix +"/favN?planName="+name,"",{withCredentials:true} ) as Observable<Plan>
  }

  dislikePlan(name:string):Observable<Plan>{
    return this.httpClient.put(this.urlPrefix +"/notFavN?planName="+name,"",{withCredentials:true} ) as Observable<Plan>
  }

  createPlan(newPlan: Plan, categoryName: string) {
    return this.httpClient.post(this.urlPrefix+"?category="+categoryName, newPlan,{withCredentials:true} ) as Observable<Plan>
  }

  getPlanByName(planName: string):Observable<Plan> {
    return this.httpClient.get("api/plans?planName="+planName) as Observable<Plan>
  }

  editPlan(data: PlanEdition, planName: string): Observable<Plan> {
    return this.httpClient.put("/api/plans/"+planName, data,{withCredentials: true}) as Observable<Plan>
  }
}
