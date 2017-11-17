import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {OauthService} from "../oauth.service";
import {Router} from "@angular/router";

class Account {
  email: String;
  username: String;
  password: String;
  edit: boolean = false;
}

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  public users: Account[] = [];
  public error: String = "";


  constructor(private http: HttpClient, public oauth: OauthService, private router: Router) {
    }

  ngOnInit(): void {
   this.getAllUser();
  }

  /*
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);

    this.http.post(this.loginUrl, body).map(...);
   */

  login(user: Account): void {
    this.oauth.login(user.username, user.password).subscribe(bla => {
      console.log(bla);
      bla.connected = true;
      this.oauth.setToken(bla);
      this.getAllUser();
    }, error => this.oauth.error(error));
  }

  logout(): void {
    this.oauth.logout();
    this.users = [];
  }

  getAllUser(): void {
    this.error = "";
    this.oauth.get<Account[]>("/account").subscribe(bla => {
      this.users = bla;
    }, error => this.oauth.error(error));
  }

  addUser(user: Account): void {
    this.oauth.post("/account", user).subscribe(bla => {
      this.onResult(bla);
    }, error => this.oauth.error(error));
  }

  editUser(current: Account, show: boolean): void {
    current.edit = show;
  }

  putUser(currentUser: String, user: Account): void {
    this.oauth.put("/account/" + currentUser, user).subscribe(bla => {
      this.onResult(bla);
    }, error => this.oauth.error(error));
  }

  deleteUser(currentUser: String): void {
    this.oauth.remove("/account/" + currentUser).subscribe(bla => {
      this.onResult(bla);
    }, error => this.oauth.error(error));
  }

  deleteAllUser(): void {
    this.oauth.remove("/account").subscribe(bla => {
      this.onResult(bla);
    }, error => this.oauth.error(error))
  }

  onResult(bla: any): void {
    if (bla["error"] != undefined) {
      console.log(bla["error"]);
      this.error = bla["error"];
    } else {
      this.getAllUser();
    }
  }
}
