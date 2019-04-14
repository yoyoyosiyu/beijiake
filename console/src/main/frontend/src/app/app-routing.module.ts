import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AboutComponent} from "./about/about.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {CategoriesComponent} from "./categories/categories.component";
import {HomeComponent} from "./home/home.component";

const routes: Routes = [
  {
    path:'',
    component: HomeComponent,
  },
  {
    path:'about',
    component: AboutComponent
  },
  {
    path:'categories/:id',
    component: CategoriesComponent
  },
  {
    path:'categories',
    component: CategoriesComponent,
  },
  {
    path:'**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
