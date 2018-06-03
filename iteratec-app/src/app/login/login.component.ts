import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Observable, Subject, Subscription} from "rxjs/index";
import {LoginService} from "../shared/services/login.service";
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material";
import {AuthService} from "../AuthService";
import {MyTodoComponent} from "../mytodo/mytodo.component";
import {HttpClient, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [AuthService]

})
export class LoginComponent implements OnInit {

  loginFail: boolean;
  subscription: Subscription;
  username: FormControl;
  password: FormControl;

  constructor(private Auth: AuthService,
              private router: Router,
              private loginService: LoginService,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.createFormControls();
    this.loginFail = false;
  }

  login(username: String, password: String)  {
    this.Auth.getUserDetails(username, password).subscribe(data => {
      if(data.status == 200) {
        this.router.navigate(['home']);
        this.Auth.setLoggedIn(true);
      } else {
        this.loginFail = true;
      }
    });
  }

  usernameFormControl = new FormControl('', [
    Validators.required,
  ]);

  createFormControls(){
    this.username = new FormControl('', Validators.required);
    this.password = new FormControl('', Validators.required);
  }
  matcher = new MyErrorStateMatcher();
}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return !!(control && control.invalid && (control.dirty || control.touched ));
  }
}
