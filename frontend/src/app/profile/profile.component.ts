import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";
import {faUserEdit} from "@fortawesome/free-solid-svg-icons";
import {Router} from "@angular/router";
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['../../assets/css/profileStylesheet.css']
})
export class ProfileComponent implements OnInit {

  faUserEdit = faUserEdit;
  cardHeader = {
    "card-header" : true,
    "admin" : this.authorization.isAdmin()
  }
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
