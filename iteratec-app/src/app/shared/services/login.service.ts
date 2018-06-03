import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable, Subject} from "rxjs/index";
import {MessageService} from "./message.service";
import {User} from "../models/user.model";

@Injectable()
export class LoginService {

  constructor(private http: HttpClient) {
  }

  public callLogin(username, password): Observable<HttpResponse<any>> {
    const observable = new Subject<HttpResponse<any>>();

    const formData: FormData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    this.http.post('/todo/login', formData, {observe: 'response'})
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
