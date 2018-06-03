import { Component, OnInit } from '@angular/core';
import {UserService} from "../UserService";
import {Router} from "@angular/router";
import {AuthService} from "../AuthService";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private user: UserService,
              private router: Router,
              private auth: AuthService) { }

  ngOnInit() {
      this.user.logout().subscribe(data => {
         if(data){
           this.router.navigate(['']);
           this.auth.setLoggedIn(false);
         }else{
           console.log("Some problem")
         }
      })
  }

}
