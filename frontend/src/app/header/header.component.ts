import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {



  constructor(public authorization: AuthorizationService) { }

  ngOnInit(): void {
  }
}
