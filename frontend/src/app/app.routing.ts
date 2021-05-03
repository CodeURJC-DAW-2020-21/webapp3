import {RouterModule} from '@angular/router';
import {IndexComponent} from './index/index.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {CategoryScreenComponent} from './category-screen/category-screen.component';
import {GetStartedComponent} from "./get-started/get-started.component";
import {ProfileComponent} from './profile/profile.component';
import {EditProfileComponent} from "./edit-profile/edit-profile.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {ExploreComponent} from './explore/explore.component';
import {CategoryInfoComponent} from "./category-info/category-info.component";
import {EditCategoryComponent} from "./edit-category/edit-category.component";
import {EditPlanComponent} from "./edit-plan/edit-plan.component";

const appRoutes = [
  { path: '', component:IndexComponent },
  { path: 'profile', component: ProfileComponent},
  { path: 'editProfile', component: EditProfileComponent},
  { path: 'explore', component: ExploreComponent},
  { path: 'aboutUs', component: AboutUsComponent },
  { path: 'categories', component: CategoryScreenComponent },
  { path: 'getStarted', component: GetStartedComponent },
  { path: 'explore', component: ExploreComponent },
  { path: 'categories/:name', component: CategoryInfoComponent},
  { path: 'editCategory/:name', component: EditCategoryComponent},
  { path: 'editPlan/:name', component: EditPlanComponent},
  { path: '**', component: PageNotFoundComponent}
]

export const routing = RouterModule.forRoot(appRoutes)
