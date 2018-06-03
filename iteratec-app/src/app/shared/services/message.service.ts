/**
 *  verwaltet alle Fehlermeldungen
 */
export class MessageService {
  // Sammlung von strings, die an einer Stelle anpassbar sein sollen
  messages: object;
  // auszugebende messages in der Komponente
  output_messages: string[] = [];
  isError: boolean;
  isSuccess: boolean;
  messageTypeNr: number;
  messageName: string;
  constructor() {
    this.messages = {
      'f_login': 'Bad username or password.',
    };
  }

  get_message(key: string) {
    return this.messages[key];
  }

  /**
   *
   * @param outputmessage messages, die über die  messages-Komponente ausgegeben werden sollen
   * @param messageType error oder success
   */
  add(outputmessage: string, messageType: string = 'info') {
    this.output_messages.push(outputmessage);
    switch (messageType) {
      case 'error': {
        this.messageTypeNr = 1;
        this.messageName = 'error';
        break;
      }
      case 'success': {
        this.messageTypeNr = 2;
        this.messageName = 'success';
        break;
      }
    }
  }


  clear() {
    this.output_messages = [];
  }
}
