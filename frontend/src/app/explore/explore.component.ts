import {ApplicationRef, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {PlanService} from './explore.service';
import {UserService} from '../service/user.service';
import {ImageService} from '../service/image.service';
import {Plan} from './explore';
import {pageable} from './explore';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['../../assets/css/style.css', "../../assets/vendor/font-awesome/css/all.css", "./explore.component.css"]
})
export class ExploreComponent implements OnInit {

  constructor(private httpClient: HttpClient,private planService: PlanService, private userService: UserService,private change:ApplicationRef) { }


  pageNumber: number;
  plans: Plan[] = [];
  registered:boolean;
  admin:boolean;
  noMorePages: boolean;

  ngOnInit() {
    this.refresh();
  }

  private getPlans(content:pageable):void{
    content.content.forEach(plan=>{
        this.plans.push(plan)});
    }


  private refresh() {
    this.pageNumber = 0;
    this.plans = [];
    this.showloader();
    this.planService.getPage(0).subscribe(
      pageable=> {
        this.registered=this.userService.isLogged();
        this.admin = this.userService.isAdmin()
        this.getPlans(pageable); this.noMorePages = pageable.last
        this.pageNumber++;
      },
      error => console.log(error),
      ()=>{
      this.hideloader();
      console.log("ended");
      });
    }

  private showloader(){
    document.getElementById("spinner").style.display='inline';
    document.getElementById("loadmore").style.display='none';
  }
    private hideloader(){
    document.getElementById("spinner").style.display='none';
      document.getElementById("loadmore").style.display='inline';

  }
  public loadMore(){
    if (!this.noMorePages) {
      this.showloader();
      this.planService.getPage(this.pageNumber).subscribe(
        pageable => {
          this.getPlans(pageable);
          this.noMorePages = pageable.last;
          this.pageNumber ++;
        },
        error =>console.log(error),
        () =>{
          this.hideloader();
        }
      )
    }
    else {
        alert("No more plans to load");
      }
  }

  public firstPlanDisplay(){
    //this function displays the second set of buttons on click
    document.getElementById("start").style.display='none';
    document.getElementById("finish").style.display='inline';
    document.getElementById("leave").style.display='inline';
  }

  public secondPlanDisplay(){
    //this function displays the first set of buttons on click
    document.getElementById("start").style.display='inline';
    document.getElementById("finish").style.display='none';
    document.getElementById("leave").style.display='none';
  }

  public CompletePlan(PlanName){
    this.secondPlanDisplay();
    this.planService.completePlan(PlanName).subscribe();
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
    this.refresh();
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
