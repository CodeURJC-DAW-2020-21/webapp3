import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";



@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ["../../../src/assets/vendor/font-awesome/css/all.css"]
})
export class HeaderComponent implements OnInit {


  constructor(public authorization: AuthorizationService) { }

  ngOnInit(): void {
  }

  public isCollapsed = true;

  toggleMenu() {
    this.isCollapsed = !this.isCollapsed;
  }

}
