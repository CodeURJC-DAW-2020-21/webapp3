import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Plan} from "../explore/explore";
import {PlanService} from "../explore/explore.service";
import {ImageService} from "../service/image.service";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-edit-plan',
  templateUrl: './edit-plan.component.html',
  styleUrls: ['../../assets/css/editProfileStyle.css', '../../assets/css/profileStylesheet.css']
})
export class EditPlanComponent implements OnInit {

  name: string;
  plan: Plan;
  img;

  constructor(private titleService: Title, private activatedRoute: ActivatedRoute, private planService: PlanService, private imageService:ImageService, private router: Router) {

  }

  ngOnInit(): void {
    this.setTitle("Growing - Edit plan")
    this.name = this.activatedRoute.snapshot.params['name'];
    this.planService.getPlanByName(this.name).subscribe(
      plan => {
        this.plan = plan;
        this.imageService.getCategoryIcon(plan.categoryName).subscribe(
          image => {
            this.createImageFromBlob(image)
          })
      }
    )

  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.img = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  editPlan (event: MouseEvent, newDescription: string, abv: string, difficulty: string){
    event.preventDefault()
    let variable = {
      newDescription: newDescription,
      abv: abv,
      difficulty: Number(difficulty)
    }
    this.planService.editPlan(variable,this.name).subscribe(
      _ => {
        this.router.navigate(['categories/'+this.plan.categoryName])
      },
      _ => {
        alert("Bad request")
      }
    )
  }
}
