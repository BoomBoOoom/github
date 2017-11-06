import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import 'rxjs/add/operator/filter';
import {HttpClient} from "@angular/common/http";
import {OauthService} from "./oauth.service";

declare var auth0: any;

@Injectable()
export class AuthService {
  // Create Auth0 web auth instance
  // @TODO: Update AUTH_CONFIG and remove .example extension in src/app/auth/auth0-variables.ts.example
   auth0 = new auth0.WebAuth({
    clientID: '0669ac311d75d255a8c24f512f8d36eb5f583d48',
    domain: 'github.com',
    responseType: 'token id_token',
    audience: 'https://github.com/BoomBoOoom/github',
    redirectUri: 'http://localhost:4200/',
    scope: 'openid'
  });

   private username: String;

  constructor(private http: HttpClient, private oauth: OauthService, private router: Router) {
  }

  public login(username: String): void {
    this.username = username;
    this.auth0.authorize();
  }

  public getUsername(): String {
    return this.username;
  }

  public handleAuthentication(): void {
    this.auth0.parseHash((err, authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        window.location.hash = '';
        this.setSession(authResult);
        this.router.navigate(['/']);
      } else if (err) {
        this.router.navigate(['/']);
        console.log(err);
      }
    });
  }

  private setSession(authResult): void {
    // Set the time that the access token will expire at
    const expiresAt = JSON.stringify((authResult.expiresIn * 1000) + new Date().getTime());
    localStorage.setItem('access_token', authResult.accessToken);
    localStorage.setItem('id_token', authResult.idToken);
    localStorage.setItem('expires_at', expiresAt);
  }

  public logout(): void {
    // Remove tokens and expiry time from localStorage
    localStorage.removeItem('access_token');
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
    // Go back to the home route
    this.router.navigate(['/']);
  }

  public isAuthenticated(): boolean {
    // Check whether the current time is past the
    // access token's expiry time
    const expiresAt = JSON.parse(localStorage.getItem('expires_at'));
    return new Date().getTime() < expiresAt;
  }

}
