import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {LoginService} from "./shared/services/login.service";
import {MessageService} from "./shared/services/message.service";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import { HomeComponent } from './home/home.component';
import {
  MatAutocompleteModule,
  MatBadgeModule,
  MatBottomSheetModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule, MatDialog,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule, MatListModule, MatMenuModule, MatNativeDateModule, MatPaginatorModule, MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule, MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule, MatSort,
  MatSortModule, MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule, MatTooltipModule,
  MatTreeModule

} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {CdkTableModule} from "@angular/cdk/table";
import { HeaderComponent } from './header/header.component';
import {EditDialog, RemoveDialog, TodoComponent} from './todo/todo.component';
import { TodoTabComponent } from './todo-tab/todo-tab.component';
import {CreateDialog1, EditDialog1, MyTodoComponent, RemoveDialog1} from './mytodo/mytodo.component';
import {EditDialog2, HistoryTodoComponent, RemoveDialog2} from './history-todo/history-todo.component';
import {Moment} from "moment";
import {AuthGuard} from "./AuthGuard";
import {AuthService} from "./AuthService";
import {UserService} from "./UserService";
import { LogoutComponent } from './logout/logout.component';
import { FieldErrorDisplayComponent } from './field-error-display/field-error-display.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    TodoComponent,
    EditDialog,
    TodoTabComponent,
    MyTodoComponent,
    RemoveDialog,
    CreateDialog1,
    EditDialog1,
    EditDialog2,
    RemoveDialog1,
    RemoveDialog2,
    HistoryTodoComponent,
    LogoutComponent,
    FieldErrorDisplayComponent
  ],
  imports: [
    CdkTableModule,
    AppRoutingModule,
    HttpClientModule,
    MatInputModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    BrowserModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonToggleModule,
    MatDialogModule,
    MatTabsModule,
    MatDatepickerModule,

  ],
  entryComponents: [EditDialog2,EditDialog1,EditDialog,TodoTabComponent,RemoveDialog,RemoveDialog1,RemoveDialog2,CreateDialog1],
  providers: [LoginService,MessageService,MyTodoComponent,AuthGuard,AuthService,UserService,TodoTabComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
