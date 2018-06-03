import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {TodoComponent} from "./todo/todo.component";
import {TodoTabComponent} from "./todo-tab/todo-tab.component";
import {AuthGuard} from "./AuthGuard";
import {LogoutComponent} from "./logout/logout.component";

const routes: Routes = [
  {path: '', component: LoginComponent
  },
  {path: 'login', component: LoginComponent
  },
  {path: 'logout', component: LogoutComponent
  },
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]
  },
  {path: 'todo', component: TodoTabComponent, canActivate: [AuthGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {

}
