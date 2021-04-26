import {Component, OnInit} from '@angular/core';
import {AuthorizationService} from "./authorization.service";

import {Router} from "@angular/router";
import {Title} from "@angular/platform-browser";
import {User} from "../model/User";
import {ImageService} from "../image/image.service";
import {ImageSnippet} from "../image/imageSnippet";
import {Observable} from "rxjs";

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


  constructor(private authorizationService: AuthorizationService, private titleService: Title, private router: Router, private imageService: ImageService) {
  }

  selectedFile: ImageSnippet;
  user: User;

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Get started")
  }


  login(event: MouseEvent, email: string, pass: string) {
    event.preventDefault();
    this.authorizationService.login(email, pass).subscribe(
      _ => {
        this.authorizationService.reqIsLogged();
        this.router.navigate([''])
      },
      _ => alert("Wrong credentials")
    )
  }

  goToSignUp() {
    this.getStartedForm["sign-up-mode"] = true;
  }

  goToSignIn() {
    this.getStartedForm["sign-up-mode"] = false;
  }


  processFile(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);

      this.imageService.uploadImage(this.selectedFile.file).subscribe(
        _ => {
          this.authorizationService.logOut()
        },
        _ => {

        })
    });

    reader.readAsDataURL(file);
  }

  createUser($event: MouseEvent, email: string, username: string, name: string, surname: string, password: string, confirmEncodedPassword: string, imageFile: any) {
    this.user = {
      email: email,
      username: username,
      name: name,
      surname: surname,
      encodedPassword: password,
      confirmEncodedPassword: confirmEncodedPassword,
      roles: ['USER'],
    }
    this.authorizationService.createUser(this.user).subscribe(
      _ => {
        this.authorizationService.login(this.user.email, this.user.encodedPassword).subscribe(
          _ => {
            if (imageFile.files.length != 0)
              this.processFile(imageFile)
            else this.router.navigate([''])},
          _ => alert('Bad request')
        )
      }
    )
  }




}
