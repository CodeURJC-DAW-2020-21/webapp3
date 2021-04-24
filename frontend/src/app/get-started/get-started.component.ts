import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "./authorization.service";

import {ActivatedRoute, Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {User} from "../model/User";

@Component({
  selector: 'app-get-started',
  templateUrl: './get-started.component.html',
  styleUrls: ['../../../src/assets/css/style.css', "../../../src/assets/css/getStarted.css", "../../../src/assets/vendor/font-awesome/css/all.css"]
})
export class GetStartedComponent implements OnInit {


  getStartedForm = {
    "container-login": true,
    "sign-up-mode": false
  }


  constructor(private authorizationService: AuthorizationService, private titleService: Title, private router: Router, activatedRoute : ActivatedRoute) { }

  user : User;

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Get started")
  }


  login(event: MouseEvent, email: string, pass: string){
      event.preventDefault();
      this.authorizationService.login(email, pass)
  }

  goToSignUp() {
    this.getStartedForm["sign-up-mode"] = true;
  }

  goToSignIn() {
    this.getStartedForm["sign-up-mode"] = false;
  }

  createUser($event: MouseEvent, email: string, username: string, name: string, surname: string, password: string, confirmEncodedPassword: string, imageFile: string){
      this.user = {email : email, username: username, name: name, surname: surname, encodedPassword : password, confirmEncodedPassword : confirmEncodedPassword, roles: ['USER']}
      console.log(this.user)
      this.authorizationService.createUser(this.user).subscribe(
        _ => this.router.navigate(['']),
        error => alert('Bad request')
      )
  }

}
