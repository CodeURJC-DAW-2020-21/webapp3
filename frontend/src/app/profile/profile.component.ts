import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";
import {Router} from "@angular/router";
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['../../assets/css/profileStylesheet.css', "../../../src/assets/vendor/font-awesome/css/all.css"]
})
export class ProfileComponent implements OnInit {


  cardHeader = {
    "card-header" : true,
    "admin" : this.authorization.isAdmin()
  }

  admin: boolean = this.authorization.isAdmin();
  constructor(public authorization: AuthorizationService, private router: Router, private titleService: Title) { }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Profile")
  }

  goToEditProfile(){
    this.router.navigate(['/books']);
  }

}
