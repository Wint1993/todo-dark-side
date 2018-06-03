import {Component, Inject, Injectable, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {HttpClient,HttpResponse} from "@angular/common/http";
import {Todo} from "../shared/models/todo.model";
import {Observable,Subject} from "rxjs/index";
import {MyTodoComponent} from "../mytodo/mytodo.component";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css'],
  providers: [MyTodoComponent]
})
@Injectable()
export class TodoComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  todos: Todo[] = [];
  displayedColumns = ['id', 'description', 'date', 'actions'];
  dataSource;

  constructor(private http: HttpClient, public dialog: MatDialog, public todo: MyTodoComponent) {
      this.getAll();
  }

  ngOnInit() {
  }

  public refresh_1() {
    this.getAll();
    console.log("Refresh");
  }

  public delete(id) {
    return this.http.delete('/todo/rest/todo/delete/' + id)
      .subscribe(res => {
        this.getAll();
      });
  }

  public getAll() {
    this.todos = [];
    this.http.get<Todo[]>('/todo/rest/todo/all')
      .subscribe((data: Todo[]) => {
        for (let i = 0; i < data.length; i++) {
          this.todos.push(Object.assign(new Todo(null, null, null), data[i]))
        }
        this.dataSource = new MatTableDataSource<Todo>(this.todos);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }, err => {
        console.log("No data!");
      })
  }

  public save(element): Observable<HttpResponse<any>> {
    let date = element.date;
    if(!(typeof date === 'string')){
      date.setHours(date.getHours() - (date.getTimezoneOffset()/60));
    }
    const observable = new Subject<HttpResponse<any>>();
    this.http.post<any>('/todo/rest/todo/update/' + element.id, {
      description: element.description,
      date: date
    })
      .subscribe(res => {
          this.getAll();
        },
        err => {
        }
      );
    return observable;
  }

  openEditDialog(element): void {
    let something = new Todo(element.id,element.description,element.date);
    let dialogRef = this.dialog.open(EditDialog, {
      width: '250px',
      data: {Todo: something}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.save(something);
      }
    });
  }

  openRemoveDialog(element): void {
    let dialogRef = this.dialog.open(RemoveDialog, {
      width: '250px',
      data: {Todo: element}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete(element.id);
      }
    });
  }
}

@Component({
  selector: 'dialog-edit-todo',
  templateUrl: 'modals/dialog-edit-todo.html',
  providers: [TodoComponent]
})
export class EditDialog implements OnInit{

  minDate = new Date(Date.now());
  form: FormGroup;
  private formSubmitAttempt: boolean;

  constructor(public dialogRef: MatDialogRef<EditDialog>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      description: [null, Validators.required],
      date: [null, Validators.required],
    })
  }

  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field)
    };
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  saveResult(): void {
    this.formSubmitAttempt = true;
    if (this.form.valid) {
      console.log('form submitted');
      this.dialogRef.close( new Todo(null,this.form.get('description').value,this.form.get('date').value));
    } else {
      console.log('form not submited');
      this.validateAllFormFields(this.form);    }

  }

  isFieldValid(field: string) {
    return (!this.form.get(field).valid && this.form.get(field).touched) ||
      (this.form.get(field).untouched && this.formSubmitAttempt);
  }

  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      console.log(field);
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });}
}

@Component({
  selector: 'dialog-remove-todo',
  templateUrl: 'modals/dialog-remove-todo.html',
  providers: [TodoComponent]
})
export class RemoveDialog {

  constructor(public dialogRef: MatDialogRef<RemoveDialog>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  remove(): void {
    this.dialogRef.close(true);
  }

}




