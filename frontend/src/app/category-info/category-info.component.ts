import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Category} from '../model/Category';
import {CategoryService} from '../category-list/category.service';
import {User} from '../model/User';
import {UserService} from './user.service';

@Component({
  templateUrl: './category-info.component.html',
  selector: 'app-category-info',
  styleUrls: ['../../assets/css/style.css']
})

export class CategoryInfoComponent implements OnInit{
  category: Category;
  categoryList: Category[];
  user: User;

  constructor(private router: Router, private categoryService: CategoryService, private userService: UserService) { }

  // tslint:disable-next-line:typedef
  ngOnInit() {
    this.categoryService.getCategories().subscribe(
      categoryList => this.categoryList = categoryList,
      error => console.log(error)
    );
    this.userService.getUser().subscribe(
      user => this.user = user,
      error => console.log(error)
    );
  }
}
