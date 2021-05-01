import { SafeUrl } from "@angular/platform-browser";

export class Category {
  name: string;
  private _color: string;
  des: string;
  imageURL:SafeUrl;

  get color(): string {
    return this._color;
  }

  set color(value: string) {
    this._color = value;
  }


}

