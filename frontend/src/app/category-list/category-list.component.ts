import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../service/category.service';
import {HttpClient} from '@angular/common/http';
import {Category} from '../model/Category';
import {DomSanitizer, Title} from "@angular/platform-browser";

@Component({
  selector: 'category-list',
  templateUrl: './category-list.component.html',
  styleUrls: []
})
export class CategoryListComponent implements OnInit {

  constructor(private categoryService: CategoryService, private httpClient: HttpClient,private sanitizer: DomSanitizer) { }

  categories: Category[] = [];

  ngOnInit() {
    this.getCategories();
  }


  public getCategories() {
    this.categoryService.getCategories().subscribe(
      category => {this.categories = category;},
      error => console.log(error))
  }
}
