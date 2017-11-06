import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {OauthService} from "./oauth.service";
import {LoginComponent} from "./login/login.component";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {LanguageComponent} from "./language/language.component";
import {SearchComponent} from "./search/search.component";
import {AuthGuard} from "./can-activate.authguard";
import {AuthService} from "./authentication.service";
import {LoginService} from "./login.service";

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'search', component: SearchComponent},
  {path: 'language', component: LanguageComponent},
  {path: '**', component: HomeComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SearchComponent,
    LanguageComponent,
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [LoginService, AuthService, AuthGuard, OauthService],
  bootstrap: [AppComponent, LoginComponent, HomeComponent, SearchComponent, LanguageComponent]
})

export class AppModule {}
