import {Component, OnInit} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {AuthorizationService} from "../get-started/authorization.service";
import {ImageSnippet} from "../image/imageSnippet";
import {ImageService} from "../image/image.service";
import {Router} from "@angular/router";
import {ProfileService} from "../profile/profile.service";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['../../assets/css/editProfileStyle.css', '../../assets/css/profileStylesheet.css']
})
export class EditProfileComponent implements OnInit {

  img;
  email: string;
  username: string;
  name: string;
  surname: string;
  admin: boolean
  selectedFile: ImageSnippet;

  constructor(private titleService: Title, private authorizationService: AuthorizationService, private imageService: ImageService, private router: Router, private profileService: ProfileService) {
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Edit profile");
    this.imageService.getProfileImage().subscribe(
      image => {
        this.createImageFromBlob(image);
      }
    );
    this.profileService.getUserInfo().subscribe(
      user => {
        this.email = user.email;
        this.username = user.username;
        this.name = user.name;
        this.surname = user.surname;
      }
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

  processFile(imageInput: any) {

    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);

      this.imageService.uploadImage(this.selectedFile.file).subscribe(
        _ => {
          this.router.navigate(['profile'])
        },
        _ => {

        })
    });

    reader.readAsDataURL(file);

  }

  editProfile(event: MouseEvent, username: string, name: string, surname: string, encodedPassword: string, confirmEncodedPassword: string, imageFile: any) {
    event.preventDefault()
    this.profileService.editProfile(username, name, surname, encodedPassword, confirmEncodedPassword).subscribe(
      _ => {
        if (imageFile.files.length > 0)
          this.processFile(imageFile)
        else
          this.router.navigate(['profile'])
      }
    )
  }
}
