import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {routing} from './app.routing';
import {AppComponent} from './app.component';
import {ChartsModule} from 'ng2-charts';
import {HttpClientModule} from '@angular/common/http';
import {HeaderComponent} from './header/header.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CategoryListComponent} from './category-list/category-list.component';
import {FooterComponent} from './footer/footer.component';
import {IndexComponent} from './index/index.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {GetStartedComponent} from './get-started/get-started.component';
import {FormsModule} from "@angular/forms";
import {BarChartComponent} from './charts/barChart/barChart.component';
import {RadarChartComponent} from './charts/radar-chart/radar-chart.component';
import {DoughnutChartComponent} from './charts/doughnut-chart/doughnut-chart.component';
import {ProfileComponent} from './profile/profile.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CategoryListComponent,
    FooterComponent,
    IndexComponent,
    AboutUsComponent,
    GetStartedComponent,
    AboutUsComponent,
    BarChartComponent,
    RadarChartComponent,
    DoughnutChartComponent,
    ProfileComponent
  ],
   
  imports: [
    BrowserModule, HttpClientModule, NgbModule, ChartsModule, routing, FontAwesomeModule, FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
