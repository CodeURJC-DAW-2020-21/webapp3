import { Component, OnInit } from '@angular/core';
import {PlanService} from './explore.service';
import {Plan} from './explore';
import {pageable} from './explore';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class ExploreComponent implements OnInit {


  constructor(private planService: PlanService) { }

  pageNumber: number=0;
  plans: Plan[] = [];
  registered:boolean;
  admin:boolean;

  ngOnInit() {
    this.refresh();
  }

  private getPlans(content:pageable):void{
    content.content.forEach(plan=>(this.plans.push(plan)))
  }
  public refresh() {
    this.registered=true;
    this.admin=false;
    this.planService.getPage(this.pageNumber++).subscribe(
      pageable=>(this.getPlans(pageable)),
      error => console.log(error));
  }


  public CompletePlan(PlanName){
    this.planService.completePlan(PlanName).subscribe(
      _=> {console.log("funciona")},
      error => console.log(error)    );


  }

  public EmptyHearth(abbv){
    this.planService.dislikePlan(abbv);
    window.location.reload();
  }

  public FillHearth(abbv){
    this.planService.likePlan(abbv);
    window.location.reload();
  }
}
