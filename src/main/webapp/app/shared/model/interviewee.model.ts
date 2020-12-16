export interface IInterviewee {
  id?: number;
  candidateId?: string;
  remaining?: number;
}

export class Interviewee implements IInterviewee {
  constructor(public id?: number, public candidateId?: string, public remaining?: number) {}
}
