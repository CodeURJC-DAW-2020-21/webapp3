import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "./authorization.service";

@Component({
  selector: 'app-get-started',
  templateUrl: './get-started.component.html',
  styleUrls: ['../../../src/assets/css/style.css', "../../../src/assets/css/getStarted.css"]
})
export class GetStartedComponent implements OnInit {


  getStartedForm = {
    "container-login": true,
    "sign-up-mode": false
  }

  constructor(private authorizationService : AuthorizationService) { }

  ngOnInit(): void {
  }

  login(event: any, email: string, pass: string){
      event.preventDefault();
      this.authorizationService.login(email, pass)
  }

  goToSignUp() {
    this.getStartedForm["sign-up-mode"] = true;
  }

  goToSignIn() {
    this.getStartedForm["sign-up-mode"] = false;
  }

}
