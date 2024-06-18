import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthentificationComponent } from './Administration/authentification/authentification.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient} from "@angular/common/http";
import {PeriodeshiftComponent} from "./Manutention/periodeshift/periodeshift.component";
import {ModetravailComponent} from "./Manutention/modetravail/modetravail.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoadingComponent } from './loading/loading.component';
import { NormeproductiviteComponent } from './Manutention/normeproductivite/normeproductivite.component';
import {httpTokenInterceptorInterceptor} from "./Interceptors/http-token-interceptor.interceptor";
import {KeycloakService} from "./services/keycloak.service";
import {NgxPaginationModule} from "ngx-pagination";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatProgressBarModule} from "@angular/material/progress-bar"
import {LoadingInterceptorInterceptor} from "./Interceptors/loading-interceptor.interceptor";
export function kcFactory(kcService : KeycloakService)  {
  return () => kcService.init();
}
@NgModule({
  declarations: [
    AppComponent,
    AuthentificationComponent,
    ModetravailComponent,
    LoadingComponent,
    NormeproductiviteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    MatProgressBarModule
  ],
    providers: [
      HttpClient,
      {
        provide : APP_INITIALIZER,
        deps : [KeycloakService],
        useFactory : kcFactory,
        multi : true
      },
      provideAnimationsAsync(),
      {
        provide : HTTP_INTERCEPTORS,
        useClass : LoadingInterceptorInterceptor,
        multi : true
      }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
