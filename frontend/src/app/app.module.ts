import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { routing } from './app.routing';
import { SwiperModule } from 'swiper/angular';
import { AppComponent } from './app.component';
import { ChartsModule } from 'ng2-charts';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './components/header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CategoryListComponent } from './components/category-list/category-list.component';
import { FooterComponent } from './components/footer/footer.component';
import { IndexComponent } from './components/index/index.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { GetStartedComponent } from './components/get-started/get-started.component';
import { FormsModule } from '@angular/forms';
import { BarChartComponent } from './components/charts/barChart/barChart.component';
import { RadarChartComponent } from './components/charts/radar-chart/radar-chart.component';
import { DoughnutChartComponent } from './components/charts/doughnut-chart/doughnut-chart.component';
import { ProfileComponent } from './components/profile/profile.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { AdminTableComponent } from './components/admin/admin-table/admin-table.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { CategoryScreenComponent } from './components/category-screen/category-screen.component';
import { ExploreComponent } from './components/explore/explore.component';
import { AddCategoryComponent } from './components/admin/add-category/add-category.component';
import { AddPlanComponent } from './components/admin/add-plan/add-plan.component';
import { EditCategoryComponent } from './components/admin/edit-category/edit-category.component';
import { EditPlanComponent } from './components/admin/edit-plan/edit-plan.component';
import { CategoryInfoComponent } from './components/category-info/category-info.component';


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
    EditPlanComponent,
    AddPlanComponent,
    CategoryInfoComponent
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
