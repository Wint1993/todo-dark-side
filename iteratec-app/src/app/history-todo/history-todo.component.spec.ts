import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryTodoComponent } from './history-todo.component';

describe('HistoryTodoComponent', () => {
  let component: HistoryTodoComponent;
  let fixture: ComponentFixture<HistoryTodoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryTodoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryTodoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
