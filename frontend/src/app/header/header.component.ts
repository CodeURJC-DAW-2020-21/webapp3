import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ["../../../src/assets/vendor/font-awesome/css/all.css"]
})
export class HeaderComponent implements OnInit {


  constructor(public authorization: UserService) { }

  ngOnInit(): void {
  }

  public isCollapsed = true;

}
