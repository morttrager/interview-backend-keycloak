export interface IQuestionSet {
  id?: number;
  name?: string;
  intro?: string;
  outro?: string;
}

export class QuestionSet implements IQuestionSet {
  constructor(public id?: number, public name?: string, public intro?: string, public outro?: string) {}
}
