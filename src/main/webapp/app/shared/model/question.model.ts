export interface IQuestion {
  id?: number;
  question?: string;
  answer?: string;
  weight?: number;
}

export class Question implements IQuestion {
  constructor(public id?: number, public question?: string, public answer?: string, public weight?: number) {}
}
