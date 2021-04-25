import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";
import {Router} from "@angular/router";
import { Title } from '@angular/platform-browser';
import {ImageService} from "../image/image.service";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['../../assets/css/profileStylesheet.css', "../../../src/assets/vendor/font-awesome/css/all.css"]
})
export class ProfileComponent implements OnInit {

  cardHeader;
  item;
  editIcon;
  img;

  constructor(public authorization: AuthorizationService, private router: Router, private titleService: Title, private imgService: ImageService) { }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Profile")
    this.cardHeader = {
      "card-header": true,
      "admin": this.authorization.isAdmin(),
    }
    this.item = {
      'item' : true,
      'admin' : this.authorization.isAdmin()
    }
    this.editIcon = {
      "fas fa-user-edit": true,
    }

    this.getProfileImage()
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
