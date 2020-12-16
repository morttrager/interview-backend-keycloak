import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInterviewQuestion } from 'app/shared/model/interview-question.model';

type EntityResponseType = HttpResponse<IInterviewQuestion>;
type EntityArrayResponseType = HttpResponse<IInterviewQuestion[]>;

@Injectable({ providedIn: 'root' })
export class InterviewQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/interview-questions';

  constructor(protected http: HttpClient) {}

  create(interviewQuestion: IInterviewQuestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interviewQuestion);
    return this.http
      .post<IInterviewQuestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(interviewQuestion: IInterviewQuestion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interviewQuestion);
    return this.http
      .put<IInterviewQuestion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInterviewQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInterviewQuestion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(interviewQuestion: IInterviewQuestion): IInterviewQuestion {
    const copy: IInterviewQuestion = Object.assign({}, interviewQuestion, {
      time: interviewQuestion.time && interviewQuestion.time.isValid() ? interviewQuestion.time.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.time = res.body.time ? moment(res.body.time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((interviewQuestion: IInterviewQuestion) => {
        interviewQuestion.time = interviewQuestion.time ? moment(interviewQuestion.time) : undefined;
      });
    }
    return res;
  }
}
