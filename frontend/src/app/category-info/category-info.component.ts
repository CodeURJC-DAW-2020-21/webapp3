import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {CategoryService} from '../service/category.service';
import { Router } from '@angular/router';
import { Category } from '../model/Category';

@Component({
  selector: 'app-category-info',
  templateUrl: './category-info.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class CategoryInfoComponent implements OnInit {

  categoryList : Category[];
  category: CategoryService;
  user: UserService;
  constructor(private router:Router, private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(
      categoryList => this.categoryList = categoryList,
      error => console.log(error)
    );
  }

}
