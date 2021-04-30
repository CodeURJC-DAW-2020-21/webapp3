import { Component, OnInit } from '@angular/core';
import { UserService } from "../../service/user.service";
import { CategoryService } from "../../service/category.service";
import { Category } from "../../model/Category";
import { Image } from "../../model/Image";


@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  constructor(public authorization: UserService, private categoryService: CategoryService) { }

  category: Category;
  selectedFile: Image;
  icon: Image;
  admin: boolean

  newCategory(event: MouseEvent, name: string, description: string, icon: any, color: string) {
    this.category = {
      name: name,
      color: color,
      description: description
    }
    this.categoryService.createCategory(this.category).subscribe(
      _ => {
        window.location.reload();
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
