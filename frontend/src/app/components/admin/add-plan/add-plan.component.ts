import { Component, Input, OnInit } from '@angular/core';
import { UserService } from "../../../service/user.service";
import { CategoryService } from "../../../service/category.service";
import { Plan } from "../../../model/Plan";
import { PlanService } from "../../../service/plan.service";
import { Router } from "@angular/router";
import { CategoryInfoComponent } from "../../category-info/category-info.component";

@Component({
  selector: 'app-add-plan',
  templateUrl: './add-plan.component.html',
  styleUrls: ['../add-category/add-category.component.css']
})
export class AddPlanComponent implements OnInit {

  constructor(public authorization: UserService, private categoryService: CategoryService, private router: Router, public planService: PlanService, private  categoryInfo: CategoryInfoComponent) { }

  @Input()
  categoryName: string;
  plan: Plan;

  ngOnInit(): void {
  }

  newPlan(event: MouseEvent, planName: string, abr: string, description: string, difficulty: string){
    this.plan = {
      planName: planName,
      abv: abr,
      description: description,
      difficulty: Number(difficulty)
    }
    this.planService.createPlan(this.plan, this.categoryName).subscribe(
      _ => {
        this.categoryInfo.refresh()
      },
      error => alert("Bad request")
    )
  }

  openCloseForm() {
    if (document.getElementById("myForm").style.display==="block"){
      document.getElementById("myForm").style.display = "none";
    }else{
      document.getElementById("myForm").style.display = "block";
    }
  }
}
