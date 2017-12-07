import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OauthService} from "../oauth.service";

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private result: any;
  public title: string = 'BoomBoom GitHub';

  constructor(private http: HttpClient, private oauth: OauthService) {
  }

  ngOnInit() {

  }

  search(term: string) {
    console.log('search service', term);
    this.http
      .get('http://localhost:8080/search/?search=' + term)
      .subscribe(data => this.result = data);
  }
}
