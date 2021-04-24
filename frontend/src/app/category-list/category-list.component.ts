import { Component, OnInit } from '@angular/core';
import {CategoryService} from './category.service';
import {HttpClient} from '@angular/common/http';
import {Category} from '../model/Category';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'category-list',
  templateUrl: './category-list.component.html',
  styleUrls: []
})
export class CategoryListComponent implements OnInit {

  constructor(private categoryService: CategoryService, private httpClient: HttpClient, private titleService: Title) { }

  categories: Category[] = [];

  ngOnInit() {
    this.setTitle("Growing - Categories")
    this.getCategories();
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }


  public getCategories() {
    this.categoryService.getCategories().subscribe(
      category => {this.categories = category},
      error => console.log(error)
    );
    return this.categories;
  }


  getCategoryIcon(categoryIcon:string){
    return this.categoryService.getCategoryIcon(categoryIcon)
  }

}
