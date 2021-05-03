import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";
import { UserService } from "../../service/user.service";
import { Image } from "../../model/Image";
import { ImageService } from "../../service/image.service";
import { Router } from "@angular/router";


class UserData{
  email: string;
  username: string;
  name: string;
  surname: string;
  roles : string[]
}

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['../../../assets/css/editProfileStyle.css', '../../../assets/css/profileStylesheet.css']
})
export class EditProfileComponent implements OnInit {

  img;
  user: UserData
  selectedFile: Image;

  constructor(private titleService: Title, private authorizationService: UserService, private imageService: ImageService, private router: Router) {
  }

  ngOnInit(): void {
    this.setTitle("Growing - Edit profile");
    this.imageService.getProfileImage().subscribe(
      image => {
        this.createImageFromBlob(image);
      }
    );
    this.authorizationService.getUserInfo().subscribe(
      user => {
        this.user = user
      },
      _ => {
        alert("You need to be registered to continue");
        this.router.navigate(['/getStarted'])
      }
    )
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
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

      this.selectedFile = new Image(event.target.result, file);

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
    let variable = {
      username : username,
      name: name,
      surname: surname,
      encodedPassword: encodedPassword,
      confirmEncodedPassword: confirmEncodedPassword
    }
    this.authorizationService.editProfile(variable).subscribe(
      _ => {
        if (imageFile.files.length > 0)
          this.processFile(imageFile)
        else
          this.router.navigate(['profile'])
      }
    )
  }
}
