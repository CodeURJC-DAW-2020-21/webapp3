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
  registered:boolean;
  admin:boolean;

  ngOnInit() {
    this.refresh();
  }

  private refresh() {
    this.registered=true;
    this.admin=false;
    this.planService.getPage(0).subscribe(
      plan => {this.plans = plan; console.log(plan)},
      error => console.log(error)
    );
  }

  public CompletePlan(PlanName){
    this.planService.completePlan(PlanName).subscribe(
      _=> {console.log("funciona")},
      error => console.log(error)    );


  }

  public EmptyHeart(abbv: string){
    this.planService.dislikePlan(abbv);
    window.location.reload();
  }

  public FillHeart(abbv: string){
    this.planService.likePlan(abbv).subscribe(
      _ => {console.log("It worked"); window.location.reload()}
    )
  }

}
