import { Component, OnInit } from '@angular/core';
import {PlanService} from './explore.service';
import {UserService} from '../service/user.service';
import {Plan} from './explore';
import {pageable} from './explore';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class ExploreComponent implements OnInit {

  constructor(private planService: PlanService,private userService: UserService) { }


  pageNumber: number=0;
  plans: Plan[] = [];
  registered:boolean;
  admin:boolean;
  noMorePages: boolean;

  ngOnInit() {
    this.refresh();
  }

  private getPlans(content:pageable):void{
    content.content.forEach(plan=>(this.plans.push(plan)))
  }

  public refresh() {
    this.registered=this.userService.isLogged();
    this.admin=this.userService.isAdmin();
    if (!this.noMorePages){
      this.planService.getPage(this.pageNumber++).subscribe(
        pageable=> {this.getPlans(pageable); this.noMorePages = pageable.last},
        error => console.log(error));
    }
    else { alert("No more plans to load"); }
  }

  public CompletePlan(PlanName){
    this.planService.completePlan(PlanName).subscribe(
      _=> {console.log("It worked")},
      error => console.log(error));
  }

  public EmptyHeart(abbv: string){
    this.planService.dislikePlan(abbv).subscribe(
      _ => {console.log("It worked"); window.location.reload()}
    );
    console.log(this.plans);
    this.ChangePlanLike(abbv);
  }

  public FillHeart(abbv: string) {
    this.planService.likePlan(abbv).subscribe(
      _ => {
        console.log("It worked");
        window.location.reload()
      });
    console.log(this.plans);
    this.ChangePlanLike(abbv);
  }
  public ChangePlanLike(abbv: string){
    for (let plan of this.plans){
      if (plan.name==abbv){
        plan.likedUser=!plan.likedUser;
        break;
      }}
  }
}
