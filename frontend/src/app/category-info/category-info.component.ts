import { Component, Input, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {CategoryService} from '../service/category.service';
import { Router } from '@angular/router';
import { Category } from '../model/Category';
import { User } from '../model/User';

@Component({
  selector: 'app-category-info',
  templateUrl: './category-info.component.html',
  styleUrls: ['../../assets/css/style.css']
})
export class CategoryInfoComponent implements OnInit {

  user: User;
  logged: boolean = false;
  constructor(private router:Router, private categoryService: CategoryService ,public userService: UserService) { }

  
  ngOnInit() {
    this.categoryService.getCategories().subscribe(
    
    )
    
  }

}
