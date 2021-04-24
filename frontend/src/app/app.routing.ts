import {RouterModule} from '@angular/router';
import {IndexComponent} from './index/index.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {CategoryListComponent} from './category-list/category-list.component';
import {GetStartedComponent} from "./get-started/get-started.component";
import {ProfileComponent} from './profile/profile.component';
import {EditProfileComponent} from "./edit-profile/edit-profile.component";

const appRoutes = [
  { path: '', component:IndexComponent },
  { path: 'profile', component: ProfileComponent},
  { path: 'editProfile', component: EditProfileComponent},
  { path: 'aboutUs', component: AboutUsComponent },
  { path: 'categories', component: CategoryListComponent },
  { path: 'getStarted', component: GetStartedComponent }
]

export const routing = RouterModule.forRoot(appRoutes)
