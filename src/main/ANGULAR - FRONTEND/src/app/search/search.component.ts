import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OauthService} from "../oauth.service";

class GitHub {
  name: String = "";
}

@Component({
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})

export class SearchComponent implements OnInit {
  private searchAccount: GitHub[];
  private name: String;
  private result: String;
  private error: String = "Not found !";

  constructor(private http: HttpClient, public oauth: OauthService) {
  }

  ngOnInit(): void {
    this.name = this.oauth.getUsername();

    this.oauth.get<GitHub[]>('/account/?search=' + this.name)
      .subscribe(data => {
        this.searchAccount = data;
        console.log(this.searchAccount);
      });
  }
}
