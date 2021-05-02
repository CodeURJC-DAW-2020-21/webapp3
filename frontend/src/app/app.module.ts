import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { routing } from './app.routing';
import { SwiperModule } from 'swiper/angular';
import { AppComponent } from './app.component';
import { ChartsModule } from 'ng2-charts';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CategoryListComponent } from './category-list/category-list.component';
import { FooterComponent } from './footer/footer.component';
import { IndexComponent } from './index/index.component';
import { AboutUsComponent } from './about-us/about-us.component';
import { GetStartedComponent } from './get-started/get-started.component';
import { FormsModule } from '@angular/forms';
import { BarChartComponent } from './charts/barChart/barChart.component';
import { RadarChartComponent } from './charts/radar-chart/radar-chart.component';
import { DoughnutChartComponent } from './charts/doughnut-chart/doughnut-chart.component';
import { ProfileComponent } from './profile/profile.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { AdminTableComponent } from './admin-table/admin-table.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CategoryScreenComponent } from './category-screen/category-screen.component';
import { ExploreComponent } from './explore/explore.component';
import { AddCategoryComponent } from './forms/add-category/add-category.component';
import { AddPlanComponent } from './forms/add-plan/add-plan.component';
import { EditCategoryComponent } from './edit-category/edit-category.component';
import { EditPlanComponent } from './edit-plan/edit-plan.component';


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
    AdminTableComponent,
    PageNotFoundComponent,
    CategoryScreenComponent,
    ExploreComponent,
    AddCategoryComponent,
    AddPlanComponent,
    EditCategoryComponent,
    EditPlanComponent
  ],

  imports: [
    BrowserModule, HttpClientModule, SwiperModule, NgbModule, ChartsModule, routing, FormsModule
  ],
  providers: [Title],
  exports: [HeaderComponent],
  bootstrap: [AppComponent, HeaderComponent]
})

export class AppModule {
}
