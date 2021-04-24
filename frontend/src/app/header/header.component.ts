import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../get-started/authorization.service";
import {faUser} from "@fortawesome/free-solid-svg-icons/faUser";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {

  faUser = faUser

  constructor(public authorization: AuthorizationService) { }

  ngOnInit(): void {
  }
}
