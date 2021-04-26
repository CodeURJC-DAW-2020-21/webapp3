import { Component, OnInit } from '@angular/core';
import {PlanService} from './explore.service';
import {Plan} from './explore';
@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class ExploreComponent implements OnInit {

  constructor(private planService: PlanService) { }

  plans: Plan[] = [];

  ngOnInit() {
    this.refresh();
  }

  private refresh() {
    this.planService.getPage(0).subscribe(
      plan => {this.plans = plan;console.log(plan)},
      error => console.log(error)
    );
  }

}
