import { Component, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-category-screen',
  templateUrl: './category-screen.component.html',
  styleUrls: []
})
export class CategoryScreenComponent implements OnInit {

  constructor(private titleService: Title) { }

  ngOnInit(): void {
    this.setTitle("Growing - Categories")
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

}
