import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";
import {AuthorizationService} from "../get-started/authorization.service";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['../../assets/css/editProfileStyle.css', '../../assets/css/profileStylesheet.css']
})
export class EditProfileComponent implements OnInit {

  user = this.authorizationService.currentUser();

  constructor(private titleService: Title, private authorizationService: AuthorizationService) {
  }
  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Edit profile")
  }

}
