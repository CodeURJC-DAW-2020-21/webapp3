export class Category {
  name: string;
  private _color: string;
  des: string;


  get color(): string {
    return this._color;
  }

  set color(value: string) {
    this._color = value;
  }
}

