import { SafeUrl } from "@angular/platform-browser";
import {Plan} from "../explore/explore";

export class Category {
  name: string;
  color: string;
  description: string;
  imageURL?:SafeUrl;
  likedByUser?:boolean
  imagePath?:string;
  plans?: Plan[];
  date?: string;
}
