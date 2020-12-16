import { Moment } from 'moment';
import { IInterviewee } from 'app/shared/model/interviewee.model';
import { IQuestion } from 'app/shared/model/question.model';
import { IInterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

export interface IInterviewQuestion {
  id?: number;
  score?: number;
  respone?: string;
  time?: Moment;
  interviewee?: IInterviewee;
  question?: IQuestion;
  interviewqs?: IInterviewQuestionSet;
}

export class InterviewQuestion implements IInterviewQuestion {
  constructor(
    public id?: number,
    public score?: number,
    public respone?: string,
    public time?: Moment,
    public interviewee?: IInterviewee,
    public question?: IQuestion,
    public interviewqs?: IInterviewQuestionSet
  ) {}
}
