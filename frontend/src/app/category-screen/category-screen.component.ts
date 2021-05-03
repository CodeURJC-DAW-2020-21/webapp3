import { Component, OnInit, ViewChild } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { CategoryListComponent } from "../category-list/category-list.component";

@Component({
  selector: 'app-category-screen',
  templateUrl: './category-screen.component.html',
  styleUrls: []
})
export class CategoryScreenComponent implements OnInit {

  @ViewChild(CategoryListComponent) categories
  constructor(private titleService: Title) { }

  ngOnInit(): void {
    this.setTitle("Growing - Categories")
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  reloadCategories(){
    this.categories.getCategories();
  }

}
