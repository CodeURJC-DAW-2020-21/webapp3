import {Component, OnInit} from '@angular/core';
import {CompletedPlan} from "../model/CompletedPlan";
import {CompletedPlanService} from "../service/completed-plan.service";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-admin-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['../../assets/css/profileStylesheet.css']
})
export class AdminTableComponent implements OnInit {

  completedPlans: CompletedPlan[]

  constructor(private completedPlanService: CompletedPlanService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.params.subscribe(_ => this.ngOnInit())
  }


  ngOnInit(): void {
    this.getCompletedPlans()

  }

  getCompletedPlans() {
    this.completedPlanService.getCompletedPlans().subscribe(
      record => this.completedPlans = record
    )
  }

  removeCompletedPlan(email: string, planName: string, date: string) {
    let completedPlan: CompletedPlan = {email: email, name: planName, date: date};
    this.completedPlanService.remove(completedPlan).subscribe(
      _ => {
        this.getCompletedPlans()
      },
      error => console.log(error)
    )
  }


  filterByEmail($event: MouseEvent, email: string) {
    $event.preventDefault();
    if (email == "") {
      this.getCompletedPlans()
    }
    else{
      this.completedPlanService.getCompletedPlansEmail(email).subscribe(
        record => this.completedPlans = record,
        _ => {
          this.completedPlans = [];
          alert("Bad request: The email does not exist");
        }
      )
    }
    if (this.completedPlans.length == 0) alert("No results for your query")
  }
}
