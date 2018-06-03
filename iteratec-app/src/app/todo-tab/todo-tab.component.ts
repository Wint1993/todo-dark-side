import {Component, Inject, OnInit, ReflectiveInjector, ViewChild} from '@angular/core';
import {MatTabChangeEvent} from "@angular/material";
import {TodoComponent} from "../todo/todo.component";
import {MyTodoComponent} from "../mytodo/mytodo.component";
import {HistoryTodoComponent} from "../history-todo/history-todo.component";
import {UserService} from "../UserService";
import {AuthGuard} from "../AuthGuard";


@Component({
  selector: 'app-todo-tab',
  templateUrl: './todo-tab.component.html',
  styleUrls: ['./todo-tab.component.css'],
  providers: [UserService],

})
export class TodoTabComponent implements OnInit {

  @ViewChild('historyTodo') historyTodo: HistoryTodoComponent;
  @ViewChild('myTodo') myTodo: MyTodoComponent;
  @ViewChild('allTodo') AllTodo: TodoComponent;

  public role: string;

  constructor(private data : AuthGuard) {
  }

  ngOnInit() {
    this.data.messageSource.subscribe(message => this.role = message);
  }

  onLinkClick(event: MatTabChangeEvent) {
    if (event.index == 0) {
      this.myTodo.getAllMy();
    }
    if(event.index == 1){
      this.historyTodo.getAllMy();
    }
    if(event.index == 2){
      this.AllTodo.getAll();
    }
  }

}
