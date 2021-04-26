import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";
import {Router} from "@angular/router";
import { Title } from '@angular/platform-browser';
import {ImageService} from "../image/image.service";
import {ProfileService} from "./profile.service";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['../../assets/css/profileStylesheet.css', "../../../src/assets/vendor/font-awesome/css/all.css"]
})
export class ProfileComponent implements OnInit {

  cardHeader;
  item;
  administratorColor;
  formName;
  formEmail;
  email: string
  username: string
  name: string
  surname: string
  admin: boolean
  img;

  constructor(public authorization: AuthorizationService, private router: Router, private titleService: Title, private imgService: ImageService, private profileService: ProfileService) { }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Profile")
    this.profileService.getUserInfo().subscribe(
      user => {
        this.email = user.email;
        this.username = user.username;
        this.name = user.name;
        this.surname = user.surname;
        this.admin = this.authorization.isAdmin()
        this.getProfileImage()
        this.cardHeader = {
          "card-header": true,
          "admin": this.admin
        };
          this.item = {
            'item' : true,
            'admin' : this.admin
          };
          this.administratorColor = {
            'fas fa-edit-user': true,
            'administrator': this.admin
          }
          this.formName = {
            name: true,
            ad: this.admin
          }
          this.formEmail = {
            email: true,
            ad: this.admin
          }
      }
    )
  }

  getProfileImage(){
    this.imgService.getProfileImage().subscribe(
      image => {this.createImageFromBlob(image);}
    )
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.img = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  goToEditProfile(){
    this.router.navigate(['editProfile']);
  }

}
