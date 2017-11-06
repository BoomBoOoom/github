import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Response, Headers} from '@angular/http';
import 'rxjs/add/operator/map';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {AUTH_CONFIG} from './auth0-variables';
import 'rxjs/add/operator/toPromise';

declare let auth0: any;

@Injectable()
export class LoginService {
  error: '';
  result: any;
  authenticated: any;
  // Create Auth0 web auth instance
  // @TODO: Update AUTH_CONFIG and remove .example extension in src/app/auth/auth0-variables.ts.example
  auth0 = new auth0.WebAuth({
    clientID: AUTH_CONFIG.CLIENT_ID,
    domain: AUTH_CONFIG.CLIENT_DOMAIN
  });

  // Create a stream of logged in status to communicate throughout app
  loggedIn: boolean;
  loggedIn$ = new BehaviorSubject<boolean>(this.loggedIn);

  constructor(private router: Router, private http: HttpClient) {
    // If authenticated, set local profile property and update login status subject
    if (this.authenticated) {
      this.setLoggedIn(true);
    }
  }

  setLoggedIn(value: boolean) {
    // Update login status subject
    this.loggedIn$.next(value);
    this.loggedIn = value;
  }

  login() {
    // Auth0 authorize request
    // Note: nonce is automatically generated: https://auth0.com/docs/libraries/auth0js/v8#using-nonce
    this.auth0.authorize({
      responseType: 'token id_token',
      redirectUri: AUTH_CONFIG.REDIRECT,
      audience: AUTH_CONFIG.AUDIENCE,
      scope: AUTH_CONFIG.SCOPE
    });
  }

  getAll() {
    return this.http.get('/users', this.jwt()).map((response: Response) => response.json());
  }

  getById(id: number) {
    return this.http.get('/users/' + id, this.jwt()).map((response: Response) => response.json());
  }

  create(user: Account) {
    return this.http.post('/users/', user, this.jwt()).map((response: Response) => response.json());
  }

  update(user: Account) {
    return this.http.put('/users/' + user.id, user, this.jwt()).map((response: Response) => response.json());
  }

  delete(id: number) {
    return this.http.delete('/users/' + id, this.jwt()).map((response: Response) => response.json());
  }

  // private helper methods

  public jwt(): Headers {
    // create authorization header with jwt token
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.token) {
      return new Headers({headers: {'Authorization': 'Bearer ' + currentUser.token}});
    }
  }

  private handleError(error: any): Promise<any> {
    console.error('Some error occured', error);
    return Promise.reject(error.message || error);
  }
}
