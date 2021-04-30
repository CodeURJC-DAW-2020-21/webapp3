import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Category} from '../model/Category';
import {CategoryService} from '../category-list/category.service';
import {User} from '../model/User';
import {AuthorizationService} from '../get-started/authorization.service';
import {Plan} from '../model/Plan';

@Component({
  templateUrl: './accordion-categories.component.html',
  selector: 'app-category-accordion',
  styleUrls: ['../../assets/css/style.css']
})

export class CategoryAccordionComponent implements OnInit{
  category: Category;
  categoryList: Category[];
  user: User;
  permissions: AuthorizationService;
  plan: Plan;
  constructor(private router: Router, private categoryService: CategoryService) { }

  // tslint:disable-next-line:typedef
  ngOnInit() {
    this.categoryService.getCategories().subscribe(
      categoryList => this.categoryList = categoryList,
      error => console.log(error)
    );
  }
}
