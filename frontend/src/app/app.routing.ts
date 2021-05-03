import { RouterModule } from '@angular/router';
import { IndexComponent } from './components/index/index.component';
import { AboutUsComponent } from './components/about-us/about-us.component';
import { CategoryScreenComponent } from './components/category-screen/category-screen.component';
import { GetStartedComponent } from "./components/get-started/get-started.component";
import { ProfileComponent } from './components/profile/profile.component';
import { EditProfileComponent } from "./components/edit-profile/edit-profile.component";
import { PageNotFoundComponent } from "./components/page-not-found/page-not-found.component";
import { ExploreComponent } from './components/explore/explore.component';
import { CategoryInfoComponent } from "./components/category-info/category-info.component";
import { EditCategoryComponent } from "./components/admin/edit-category/edit-category.component";
import { EditPlanComponent } from "./components/admin/edit-plan/edit-plan.component";

const appRoutes = [
  { path: '', component:IndexComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'editProfile', component: EditProfileComponent },
  { path: 'explore', component: ExploreComponent },
  { path: 'aboutUs', component: AboutUsComponent },
  { path: 'categories', component: CategoryScreenComponent },
  { path: 'getStarted', component: GetStartedComponent },
  { path: 'explore', component: ExploreComponent },
  { path: 'categories/:name', component: CategoryInfoComponent },
  { path: 'editCategory/:name', component: EditCategoryComponent },
  { path: 'editPlan/:name', component: EditPlanComponent},
  { path: '**', component: PageNotFoundComponent }
]

export const routing = RouterModule.forRoot(appRoutes)
