import {RouterModule} from '@angular/router';
import {IndexComponent} from './index/index.component';
import {CategoryListComponent} from './category-list/category-list.component';

const appRoutes = [
  { path:'', component:IndexComponent },
  { path:'categories', component: CategoryListComponent}
]

export const routing = RouterModule.forRoot(appRoutes)
