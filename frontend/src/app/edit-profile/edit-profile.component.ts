import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";
import {AuthorizationService} from "../get-started/authorization.service";
import {ImageSnippet} from "../image/imageSnippet";
import {ImageService} from "../image/image.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['../../assets/css/editProfileStyle.css', '../../assets/css/profileStylesheet.css']
})
export class EditProfileComponent implements OnInit {

  user = this.authorizationService.currentUser();
  selectedFile: ImageSnippet;

  constructor(private titleService: Title, private authorizationService: AuthorizationService, private imageService: ImageService, private router: Router) {
  }
  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Edit profile");
    this.imageService.getProfileImage();
  }


  processFile(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);

      this.imageService.uploadImage(this.selectedFile.file).subscribe(
        (res) => {
              this.router.navigate(['profile'])
        },
        (err) => {

        })
    });

    reader.readAsDataURL(file);
  }

}
