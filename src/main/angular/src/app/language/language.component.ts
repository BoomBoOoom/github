import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OauthService} from "../oauth.service";

@Component({
  templateUrl: './language.component.html',
  styleUrls: ['./language.component.css']
})
export class LanguageComponent implements OnInit {


  constructor(private http: HttpClient, public oauth: OauthService) {
  }

  ngOnInit(): void {

  }
}
