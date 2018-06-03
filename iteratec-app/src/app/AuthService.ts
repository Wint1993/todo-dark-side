import {HttpClient, HttpResponse} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable, Subject} from "rxjs/index";

interface myData {
  success: boolean,
  message: string
}

@Injectable()
export class AuthService {

  private loggedInStatus = false;

  constructor(private http: HttpClient) { }

  setLoggedIn(value: boolean) {
    this.loggedInStatus = value
  }

  get isLoggedIn() {
    return this.loggedInStatus
  }

  getUserDetails(username, password): Observable<HttpResponse<any>> {
    const observable = new Subject<HttpResponse<any>>();

    const formData: FormData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

   this.http.post<myData>('/todo/login', formData,{observe: 'response'})
     .subscribe(res => {
         observable.next(res);
       },
       err => {
         observable.next(err);
       }
     );
    return observable;

  }

}
