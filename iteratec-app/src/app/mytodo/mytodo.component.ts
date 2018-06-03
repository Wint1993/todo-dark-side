import {Component, forwardRef, Inject, OnInit, ViewChild} from '@angular/core';
import {Observable, Subject} from "rxjs/index";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Todo} from "../shared/models/todo.model";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-mytodo',
  templateUrl: './mytodo.component.html',
  styleUrls: ['./mytodo.component.css'],
})
export class MyTodoComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  public todos: Todo[] = [];
  public displayedColumns = ['id', 'description', 'date', 'actions'];
  public dataSource1;

  constructor(private http: HttpClient, public dialog: MatDialog) {
    this.getAllMy();
  }

  ngOnInit() {
  }

  public delete(id) {
    console.log(id);
    return this.http.delete('/todo/rest/todo/delete/' + id)
      .subscribe(res => {
        this.getAllMy();
      });
  }

  public getAllMy() {
    this.todos = [];
    this.http.get<Todo[]>('/todo/rest/todo/future')
      .subscribe((data: Todo[]) => {
        for (let i = 0; i < data.length; i++) {
          this.todos.push(Object.assign(new Todo(null, null, null), data[i]))
        }
        this.dataSource1 = new MatTableDataSource<Todo>(this.todos);
        this.dataSource1.paginator = this.paginator;
        this.dataSource1.sort = this.sort;
      }, err => {
        console.log("No data!");
      })
  }

  public refresh_1() {
    this.getAllMy();
    console.log("Refresh");
  }

  public save(element): Observable<HttpResponse<any>> {
    let date = element.date;
    if (!(typeof date === 'string')) {
      date.setHours(date.getHours() - (date.getTimezoneOffset() / 60));
    }
    const observable = new Subject<HttpResponse<any>>();
    this.http.post<any>('/todo/rest/todo/update/' + element.id, {
      description: element.description,
      date: date
    })
      .subscribe(res => {
          this.getAllMy();
        },
        err => {
        }
      );
    return observable;
  }

  public create(element): Observable<HttpResponse<any>> {
    if (element.date == null) {
      return null;
    }
    let date = element.date;
    console.log(element);
    if (!(typeof date === 'string')) {
      date.setHours(date.getHours() - (date.getTimezoneOffset() / 60));
    }
    const observable = new Subject<HttpResponse<any>>();
    this.http.put<any>('/todo/rest/todo/create', {
      description: element.description,
      date: date
    })
      .subscribe(res => {
          this.getAllMy();
        },
        err => {
        }
      );
    return observable;
  }

  openEditDialog(element): void {
    let something = new Todo(element.id, element.description, element.date);
    let dialogRef = this.dialog.open(EditDialog1, {
      width: '250px',
      data: {Todo: something}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.save(something);
      }
    });
  }

  openRemoveDialog(element): void {
    let dialogRef = this.dialog.open(RemoveDialog1, {
      width: '250px',
      data: {Todo: element}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.delete(element.id);
      }
    });
  }

  openCreateDialog(): void {
    let dialogRef = this.dialog.open(CreateDialog1, {
      width: '250px',
      data: {Todo: null}

    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.create(result);

      }
    });
  }

}

@Component({
  selector: 'dialog-edit-todo',
  templateUrl: 'modals/dialog-edit-todo.html',
  providers: [MyTodoComponent]
})
export class EditDialog1 implements OnInit {
  minDate = new Date(Date.now());
  form: FormGroup;
  private formSubmitAttempt: boolean;

  constructor(public dialogRef: MatDialogRef<EditDialog1>,
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
      this.dialogRef.close(new Todo(null, this.form.get('description').value, this.form.get('date').value));
    } else {
      console.log('form not submited');
      this.validateAllFormFields(this.form);
    }


    //this.dialogRef.close(true);
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
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

}

@Component({
  selector: 'dialog-create-todo',
  templateUrl: 'modals/dialog-create-todo.html',
  providers: [MyTodoComponent]
})
export class CreateDialog1 implements OnInit {
  minDate = new Date(Date.now());
  form: FormGroup;
  private formSubmitAttempt: boolean;

  constructor(public dialogRef: MatDialogRef<CreateDialog1>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      description: [null, Validators.required],
      date: [null, Validators.required],
    })
  }

  isFieldValid(field: string) {
    return (!this.form.get(field).valid && this.form.get(field).touched) ||
      (this.form.get(field).untouched && this.formSubmitAttempt);
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

  saveResult(data): void {
    this.formSubmitAttempt = true;

    if (this.form.valid) {
      console.log('form submitted');
      this.dialogRef.close(new Todo(null, this.form.get('description').value, this.form.get('date').value));
    } else {
      console.log('form not submited');

      this.validateAllFormFields(this.form);
    }
  }

  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      console.log(field);
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }
}

@Component({
  selector: 'dialog-remove-todo',
  templateUrl: 'modals/dialog-remove-todo.html',
  providers: [MyTodoComponent]
})
export class RemoveDialog1 {

  constructor(public dialogRef: MatDialogRef<RemoveDialog1>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  remove(): void {
    this.dialogRef.close(true);
  }
}
