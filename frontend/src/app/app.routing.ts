import {RouterModule} from '@angular/router';
import {IndexComponent} from './index/index.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {CategoryListComponent} from './category-list/category-list.component';
import {ExploreComponent} from './explore/explore.component';

const appRoutes = [
  { path:'', component:IndexComponent },
  { path:'aboutUs', component: AboutUsComponent },
  { path:'categories', component: CategoryListComponent },
	{path:'explore', component: ExploreComponent }
]

export const routing = RouterModule.forRoot(appRoutes)
