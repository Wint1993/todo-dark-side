import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {Observable, Subject} from "rxjs/index";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Todo} from "../shared/models/todo.model";

@Component({
  selector: 'app-history-todo',
  templateUrl: './history-todo.component.html',
  styleUrls: ['./history-todo.component.css']
})
export class HistoryTodoComponent implements OnInit {

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  todos: Todo[] = [];
  displayedColumns = ['id', 'description', 'date', 'actions'];
  dataSource1;

  constructor(private http: HttpClient, public dialog: MatDialog) {
    this.getAllMy();
  }

  ngOnInit() {

  }

  public refresh_1() {
    this.getAllMy();
    console.log("Refresh");
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
    this.http.get<Todo[]>('/todo/rest/todo/history')
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
          this.getAllMy();
        },
        err => {
        }
      );
    return observable;
  }

  openEditDialog(element): void {
    let something = new Todo(element.id,element.description,element.date);
    let dialogRef = this.dialog.open(EditDialog2, {
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
    let dialogRef = this.dialog.open(RemoveDialog2, {
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
  providers: [HistoryTodoComponent]
})
export class EditDialog2 {

  constructor(public dialogRef: MatDialogRef<EditDialog2>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  saveResult(): void {
    this.dialogRef.close(true);}

}

@Component({
  selector: 'dialog-remove-todo',
  templateUrl: 'modals/dialog-remove-todo.html',
  providers: [HistoryTodoComponent]
})
export class RemoveDialog2 {

  constructor(public dialogRef: MatDialogRef<RemoveDialog2>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  remove(): void {
    this.dialogRef.close(true);
  }
}

