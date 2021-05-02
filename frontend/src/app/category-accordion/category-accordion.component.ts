import { HttpClient } from '@angular/common/http';
import { ApplicationRef, Component, OnInit } from '@angular/core';
import { PlanService } from '../explore/explore.service';
import { UserService } from '../service/user.service';
import {Plan} from '../model/Plan';
import { pageable } from '../explore/explore';

@Component({
  selector: 'app-category-accordion',
  templateUrl: './category-accordion.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class CategoryAccordionComponent implements OnInit {

  constructor(private httpClient: HttpClient,private planService: PlanService, private userService: UserService,private change:ApplicationRef) { }

  pageNumber: number;
  plans: Plan[] = [];
  user: UserService;
  noMorePages: boolean;

  ngOnInit(){
    this.pageNumber = 0;
    this.plans = [];
    this.showloader();
    this.planService.getPage(0).subscribe(
      pageable=> {
        this.getPlans(pageable); this.noMorePages = pageable.last
        this.pageNumber++;
      },
      error => console.log(error)
    );
  }

  private getPlans(content: pageable):void{
    content.content.forEach(plan=>{
        this.plans.push(plan)});
    }

  private showloader(){
    document.getElementById("spinner").style.display='inline';
    document.getElementById("loadmore").style.display='none';
  }

  public firstPlanDisplay(){
    //this function displays the second set of buttons on click
    document.getElementById("start").style.display='none';
    document.getElementById("finish").style.display='inline';
    document.getElementById("leave").style.display='inline';
  }

  public CompletePlan(PlanName){
    this.secondPlanDisplay();
    this.planService.completePlan(PlanName).subscribe();
  }

  public secondPlanDisplay(){
    //this function displays the first set of buttons on click
    document.getElementById("start").style.display='inline';
    document.getElementById("finish").style.display='none';
    document.getElementById("leave").style.display='none';
  }

  public EmptyHeart(abbv: string){
    this.planService.dislikePlan(abbv).subscribe(
      _ =>  {
        this.recoverPages()
      }
    )
  }

  public FillHeart(abbv: string){
    this.planService.likePlan(abbv).subscribe(
      _ =>  {
        this.recoverPages()
      }
    )
  }

  private recoverPages(){
    var actualPage = this.pageNumber;
    this.ngOnInit;
    for (let _i = 1; _i <= actualPage; _i++){
      this.planService.getPage(0).subscribe(
        pageable => {
          this.getPlans(pageable);
          this.noMorePages = pageable.last
        }
      )
    }
  }

}
