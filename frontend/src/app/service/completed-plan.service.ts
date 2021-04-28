import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {CompletedPlan} from "../model/CompletedPlan";
import {catchError, map} from "rxjs/operators";

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

  remove(completedPlan: CompletedPlan) {
    return this.httpClient.delete(this.urlPrefix + 'completedPlans/' + completedPlan.email + '/' + completedPlan.name + '/' + completedPlan.date).pipe(
      catchError(error => this.handleError(error))
    )
  }

  private handleError(error: any) {
    console.log("ERROR:");
    console.error(error);
    return throwError("Server error (" + error.status + "): " )
  }

  getCompletedPlansEmail(email: string): Observable<CompletedPlan[]> {
    return this.httpClient.get("api/users/completedPlans?email=" + email, {withCredentials: true}).pipe(
      map(response => this.extractDataRegistered(email, response as any)),
      catchError(error => this.handleError(error))
    ) as Observable<CompletedPlan[]>
  }

  private extractDataRegistered(email:string, response){
    return response.map(completedPlan => this.object = {email : email, name: completedPlan.plan.name, date: completedPlan.date})
  }
}
