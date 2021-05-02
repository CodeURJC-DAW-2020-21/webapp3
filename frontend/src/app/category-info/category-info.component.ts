import {ApplicationRef, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {PlanService} from '../explore/explore.service';
import {UserService} from '../service/user.service';
import {ImageService} from '../service/image.service';
import {Plan} from '../explore/explore';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-category-info',
  templateUrl: './category-info.component.html',
  styleUrls: ['./category-info.component.css']
})
export class CategoryInfoComponent implements OnInit {

  constructor(private httpClient: HttpClient,private planService: PlanService, private userService: UserService,private change:ApplicationRef) { }

  ngOnInit() {
    this.refresh();
  }

  plans: Plan[]=[];
  registered:boolean;
  admin:boolean;

  private refresh() {
    this.plans = [];
    this.planService.getPageByCat().subscribe(
      pageable=> {
        this.registered=this.userService.isLogged();
        this.admin = this.userService.isAdmin()
      },
      error => console.log(error)
      );
    
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
    this.refresh();
  }
}
