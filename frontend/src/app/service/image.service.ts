import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { DomSanitizer } from '@angular/platform-browser';


@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private httpClient: HttpClient,private sanitizer:DomSanitizer) {}


  public uploadImage(image: File): Observable<Object> {
    const formData = new FormData();

    formData.append('imageFile', image, image.name);

    return this.httpClient.put('/api/users/profile/image', formData ,{withCredentials:true});
  }

  public getProfileImage(): Observable<Blob> {
    return this.httpClient.get('/api/users/profile/image', {withCredentials: true, responseType: 'blob'}) as Observable<Blob>;
  }


}
