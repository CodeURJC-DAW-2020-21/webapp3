import {RouterModule} from '@angular/router';
import {IndexComponent} from './index/index.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {CategoryListComponent} from './category-list/category-list.component';
import { ProfileComponent } from './profile/profile.component';

const appRoutes = [
  { path:'', component:IndexComponent },
  { path:'profile', component: ProfileComponent },
  { path:'aboutUs', component: AboutUsComponent },
  { path:'categories', component: CategoryListComponent }
]

export const routing = RouterModule.forRoot(appRoutes)
