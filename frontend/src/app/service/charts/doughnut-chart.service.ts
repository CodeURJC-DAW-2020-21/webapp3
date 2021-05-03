import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ChartData } from "../../model/ChartData";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class DoughnutChartService {

  chartData: ChartData = {name: [], data: [], color: []};
  constructor(private httpClient: HttpClient) { }

  getData(): Observable<ChartData>{
    return this.httpClient.get('/api/users/profile/favPlans', {withCredentials:true}).pipe(
      map(response => this.extractData(response as any))
    ) as Observable<ChartData>
  }

  extractData(resp){
    resp.map(res => {
      this.chartData.name.push(res.name);
      this.chartData.data.push(res.data);
      this.chartData.color.push(res.color);
      this.chartData = {
        name : this.chartData.name,
        data : this.chartData.data,
        color: this.chartData.color
      }
    });
    return this.chartData
  }
}
