import { Component, OnInit } from '@angular/core';
import { UserService } from "../../service/user.service";
import { CategoryService } from "../../service/category.service";
import { Category } from "../../model/Category";
import { Image } from "../../model/Image";
import { Router } from "@angular/router";
import { ImageService } from "../../service/image.service";


@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})

export class AddCategoryComponent implements OnInit {

  constructor(public authorization: UserService, private categoryService: CategoryService, private router: Router, private imageService: ImageService) { }

  category: Category;
  selectedFile: Image;
  admin: boolean

  processFile(icon: any, name: string) {
    const file: File = icon.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.selectedFile = new Image(event.target.result, file);

      this.imageService.addIconCategory(this.selectedFile.file,name).subscribe(
        _ => {
          this.router.navigate(['categories/'+this.category.name])
        })
    });
    reader.readAsDataURL(file);
  }

  newCategory(event: MouseEvent, name: string, description: string, icon: any, color: string) {
    this.category = {
      name: name,
      color: color,
      description: description
    }
    this.categoryService.createCategory(this.category).subscribe(
      _ => {
        if (icon.files.length != 0)
          this.processFile(icon, this.category.name)
    },
      _ => alert('Bad request')
    );
  }

  openCloseForm() {
    if (document.getElementById("myForm").style.display==="block"){
      document.getElementById("myForm").style.display = "none";
    }else{
      document.getElementById("myForm").style.display = "block";
    }
  }

  ngOnInit(): void {

  }

}
