import {NgModule} from '@angular/core';
import {BrowserModule, Title} from '@angular/platform-browser';
import {routing} from './app.routing';
import { SwiperModule } from 'swiper/angular';
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
import {FormsModule} from '@angular/forms';
import {BarChartComponent} from './charts/barChart/barChart.component';
import {RadarChartComponent} from './charts/radar-chart/radar-chart.component';
import {DoughnutChartComponent} from './charts/doughnut-chart/doughnut-chart.component';
import {ProfileComponent} from './profile/profile.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {EditProfileComponent} from './edit-profile/edit-profile.component';
import { AdminTableComponent } from './admin-table/admin-table.component';


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
    ProfileComponent,
    EditProfileComponent,
    AdminTableComponent
  ],

  imports: [
    BrowserModule, HttpClientModule, SwiperModule, NgbModule, ChartsModule, routing, FontAwesomeModule, FormsModule
  ],
  providers: [Title],
  bootstrap: [AppComponent]
})
export class AppModule {
}
