import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CompletedPlan} from "../model/CompletedPlan";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CompletedPlanService {

  urlPrefix = '/api/plans/'
  object: CompletedPlan;
  constructor(private httpClient: HttpClient) { }

  getCompletedPlans(): Observable<CompletedPlan[]> {
    return this.httpClient.get(this.urlPrefix + 'completedPlans', {withCredentials: true}).pipe(
      map(response => this.extractData(response as any))
    ) as Observable<CompletedPlan[]>
  }

  private extractData(response){
    return response.map(completedPlan => this.object = {email :completedPlan.user.email, name: completedPlan.plan.name, date: completedPlan.date})
  }

}
