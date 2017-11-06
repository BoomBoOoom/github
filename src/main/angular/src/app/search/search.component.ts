import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OauthService} from "../oauth.service";

@Component({
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {


  constructor(private http: HttpClient, public oauth: OauthService) {
  }

  ngOnInit(): void {
  }
}
