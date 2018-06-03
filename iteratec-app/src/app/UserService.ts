import {Observable} from "rxjs/index";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";

interface myData {
  message: string,
  success: boolean
}

interface isLoggedIn {
  status: boolean,
  username: string,
  role: string
}

interface logoutStatus {
  success: boolean
}



@Injectable()
export class UserService {

  private username: string;
  private role: string;

  setUsername(value: string){
    this.username = value;
  }

  get Username(){
    return this.username;
  }

  setRole(value: string){
    this.role = value;
  }

  get Role(){
    return this.role;
  }

  constructor(private http: HttpClient) { }

  isLoggedIn(): Observable<isLoggedIn> {
    return this.http.get<isLoggedIn>('/todo/rest/user/islogged1');
  }

  logout() {
    return this.http.get<logoutStatus>('/todo/logout')
  }
}
