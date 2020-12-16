import { IQTag } from 'app/shared/model/q-tag.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IQuestionTag {
  id?: number;
  qtag?: IQTag;
  question?: IQuestion;
}

export class QuestionTag implements IQuestionTag {
  constructor(public id?: number, public qtag?: IQTag, public question?: IQuestion) {}
}
