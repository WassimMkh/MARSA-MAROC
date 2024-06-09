import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthentificationComponent} from "./Administration/authentification/authentification.component";

const routes: Routes = [
  {path:"",redirectTo:"authentification",pathMatch:"full"},
  {path:"authentification",component:AuthentificationComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }