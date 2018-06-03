import {map} from "rxjs/internal/operators";
import {BehaviorSubject, Observable} from "rxjs/index";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {UserService} from "./UserService";
import {AuthService} from "./AuthService";
import {Component, Injectable} from "@angular/core";
import {TodoTabComponent} from "./todo-tab/todo-tab.component";



@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private auth: AuthService,
              private router: Router,
              private user: UserService) {
  }

  public route: string = "";

  public messageSource ;

  changeMessage(message: string) {
    this.messageSource.next(message)
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if(this.auth.isLoggedIn) {
      return true
    }
    return this.user.isLoggedIn().pipe(map(res => {
      if(res) {
        this.messageSource =  new BehaviorSubject<string>(res.role).asObservable();

        this.auth.setLoggedIn(true);
           return true
      } else {
        this.router.navigate(['login']);
           return false
      }
    }))
  }
}
