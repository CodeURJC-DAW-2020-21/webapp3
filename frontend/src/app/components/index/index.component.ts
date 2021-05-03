import { Component, OnInit } from '@angular/core';
import { Title } from "@angular/platform-browser";

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: []
})
export class IndexComponent implements OnInit {

  constructor(private titleService: Title) {
  }

  ngOnInit(): void {
    this.setTitle("Growing - Welcome");
  }

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

}
