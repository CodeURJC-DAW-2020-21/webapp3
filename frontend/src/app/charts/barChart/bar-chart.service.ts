import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {ChartData} from "../../model/ChartData";
import {map} from "rxjs/operators";

const urlPrefix = 'generateBarChart'

@Injectable({
  providedIn: 'root'
})
export class BarChartService {

  constructor(private httpClient: HttpClient) { }

  getData():Observable<ChartData[]> {
    return this.httpClient.get("api/users/profile/treeHeight", {withCredentials: true}).pipe(
      map()
    ) as Observable<ChartData[]>;
  }
}
