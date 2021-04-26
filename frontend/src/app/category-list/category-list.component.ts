import { Component, OnInit } from '@angular/core';
import {CategoryService} from './category.service';
import {Category} from './Category';

@Component({
  selector: 'category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class CategoryListComponent implements OnInit {

  constructor(private categoryService: CategoryService) { }

  categories: Category[] = [];

  ngOnInit() {
    this.refresh();
  }

  private refresh() {
    this.categoryService.getCategories().subscribe(
      category => {this.categories = category},
      error => console.log(error)
    );
  }
  public getCategoryIcon(categoryName:string){
    this.categoryService.getIcon(categoryName).subscribe(
      image => {this.createImage(image); console.log(image)},
      error => console.log(error)
    );
  }

  createImage(image:Blob) {
    const reader = new FileReader();
    reader.readAsDataURL(image);
  }
}
