import { Component, OnInit } from '@angular/core';
import {CompletedPlan} from "../model/CompletedPlan";
import {CompletedPlanService} from "./completed-plan.service";

@Component({
  selector: 'app-admin-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['../../assets/css/profileStylesheet.css']
})
export class AdminTableComponent implements OnInit {

  completedPlans : CompletedPlan[]
  constructor(private completedPlanService : CompletedPlanService) { }

  ngOnInit(): void {
    this.getCompletedPlans()
  }

  getCompletedPlans(){
    this.completedPlanService.getCompletedPlans().subscribe(
        record => {this.completedPlans = record; console.log(record)}
    )
  }

}
