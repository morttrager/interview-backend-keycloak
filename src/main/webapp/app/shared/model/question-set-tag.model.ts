import { IQuestionSet } from 'app/shared/model/question-set.model';
import { IQTag } from 'app/shared/model/q-tag.model';

export interface IQuestionSetTag {
  id?: number;
  qCount?: number;
  questionset?: IQuestionSet;
  qtag?: IQTag;
}

export class QuestionSetTag implements IQuestionSetTag {
  constructor(public id?: number, public qCount?: number, public questionset?: IQuestionSet, public qtag?: IQTag) {}
}
