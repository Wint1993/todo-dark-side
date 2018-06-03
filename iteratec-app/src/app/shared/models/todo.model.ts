export class Todo {

  id: number;
  description: string;
  date: Date;

  constructor(id: number, description: string, date: Date) {
    this.id = id;
    this.description = description;
    this.date = date;
  }

}

