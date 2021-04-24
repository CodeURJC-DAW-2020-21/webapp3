import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { routing } from './app.routing';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CategoryListComponent } from './category-list/category-list.component';
import { FooterComponent } from './footer/footer.component';
import { IndexComponent } from './index/index.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { GetStartedComponent } from './get-started/get-started.component';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CategoryListComponent,
    FooterComponent,
    IndexComponent,
    AboutUsComponent,
    GetStartedComponent
  ],
    imports: [
        BrowserModule, HttpClientModule, NgbModule, routing, FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
