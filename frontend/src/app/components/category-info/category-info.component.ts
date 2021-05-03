import { ApplicationRef, Component, OnInit } from '@angular/core';
import { PlanService } from '../../service/plan.service';
import { UserService } from '../../service/user.service';
import { Plan } from '../../model/Plan';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from "@angular/router";
import { CategoryService } from "../../service/category.service";
import { Category } from "../../model/Category";

@Component({
  selector: 'app-category-info',
  templateUrl: './category-info.component.html',
  styleUrls: ['./categoryInfo.css', '../../../assets/css/category.css']
})
export class CategoryInfoComponent implements OnInit {

  category: Category;
  plans: Plan[]=[];
  registered:boolean;
  admin:boolean;
  name: string;

  constructor(private httpClient: HttpClient,private categoryService: CategoryService, private planService: PlanService, private userService: UserService,private change:ApplicationRef,
              activatedRoute: ActivatedRoute) {
    this.name = activatedRoute.snapshot.params['name'];
  }

  ngOnInit() {
    this.refresh();
  }

  public refresh() {
    this.userService.getUserInfo().subscribe(
      _ => {
        this.categoryService.getCategory(this.name).subscribe(
          category => {
            this.category = category[1];
            this.category.imagePath = category[0].imagePath;
            this.category.date = category[0].date;
            this.plans = category[1].plans;
          }
        )
        this.registered = this.userService.isLogged();
        this.admin = this.userService.isAdmin();
      },
      _ => this.categoryService.getCategoryNotRegistered(this.name).subscribe(
        category => {
          this.category = category[0];
          this.plans = this.category.plans;
        }
      )
    )

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
    this.planService.completePlan(PlanName).subscribe(
      _=>{this.refresh()}
    );
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

  public dislikeCategory(name:string){
    this.categoryService.dislikeCategory(this.category.name).subscribe(
      _ => {
        this.recoverPages()
      }
    )
  }

  public likeCategory(name:string){
    this.category.likedByUser=!this.category.likedByUser;
    this.categoryService.likeCategory(this.category.name).subscribe(
      _ => {
        this.recoverPages()
      }
    )
  }
}
