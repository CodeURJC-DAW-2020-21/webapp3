import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: []
})
export class AboutUsComponent implements OnInit {

  constructor(private titleService: Title) {
  }
  ngOnInit() {
    this.setTitle("Growing - About us")

  }
  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

}
