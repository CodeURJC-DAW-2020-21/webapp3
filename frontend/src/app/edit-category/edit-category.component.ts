import { Component, OnInit } from '@angular/core';
import {Category} from "../model/Category";
import {Image} from "../model/Image";
import {Title} from "@angular/platform-browser";
import {ActivatedRoute, Router} from "@angular/router";
import {PlanService} from "../explore/explore.service";
import {ImageService} from "../service/image.service";
import {CategoryService} from "../service/category.service";

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css']
})
export class EditCategoryComponent implements OnInit {

  img;
  categoryName: string;
  category: Category;
  selectedFile: Image;

  constructor(private titleService: Title, private activatedRoute: ActivatedRoute, private categoryService: CategoryService, private imageService:ImageService, private router: Router) { }

  ngOnInit(): void {
    this.setTitle("Growing - Edit plan")
    this.categoryName = this.activatedRoute.snapshot.params['categoryName']
    this.categoryService.getCategoryByName(this.categoryName).subscribe(
      category => {
        this.category = category
      }
    )
    this.imageService.getCategoryIcon(this.categoryName).subscribe(
      image => {
        this.createImageFromBlob(image);
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

  processFile(icon: any) {
    const file: File = icon.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new Image(event.target.result, file);

      this.imageService.addIconCategory(this.selectedFile.file, this.categoryName).subscribe(
        _ => {
          this.router.navigate(['categories'])
        })
    });
    reader.readAsDataURL(file);
  }

  editCategory(event: MouseEvent, newDescription: string, color: string, icon: any ) {
    event.preventDefault()
    let variable = {
      description: newDescription,
      color: color,
    }
    this.categoryService.editCategory(variable, this.categoryName).subscribe(
      _ => {
        if (icon.files.length > 0)
          this.processFile(icon)
        else
          this.router.navigate(['categories'])
      }
    )

  }

}
