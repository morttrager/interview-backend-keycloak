import { Moment } from 'moment';
import { IQuestionSet } from 'app/shared/model/question-set.model';
import { IInterviewee } from 'app/shared/model/interviewee.model';

export interface IInterviewQuestionSet {
  id?: number;
  active?: boolean;
  abandoned?: boolean;
  netScore?: number;
  netQuestions?: number;
  netWeight?: number;
  time?: Moment;
  questionset?: IQuestionSet;
  interviewee?: IInterviewee;
}

export class InterviewQuestionSet implements IInterviewQuestionSet {
  constructor(
    public id?: number,
    public active?: boolean,
    public abandoned?: boolean,
    public netScore?: number,
    public netQuestions?: number,
    public netWeight?: number,
    public time?: Moment,
    public questionset?: IQuestionSet,
    public interviewee?: IInterviewee
  ) {
    this.active = this.active || false;
    this.abandoned = this.abandoned || false;
  }
}
