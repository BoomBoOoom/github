import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivate } from '@angular/router';
import {AuthService} from './authentication.service';


@Injectable()

export class AuthGuard implements CanActivate {

 constructor(private auth: AuthService, private router: Router) {}

canActivate() {
  /*  if (this.auth.login()) {
     return true;
   } else {
     this.router.navigate(['/']);
     return false;
   }*/
  return true;
 }
}
