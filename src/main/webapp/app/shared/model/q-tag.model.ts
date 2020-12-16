export interface IQTag {
  id?: number;
  name?: string;
}

export class QTag implements IQTag {
  constructor(public id?: number, public name?: string) {}
}
