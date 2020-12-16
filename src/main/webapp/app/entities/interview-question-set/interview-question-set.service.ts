import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

type EntityResponseType = HttpResponse<IInterviewQuestionSet>;
type EntityArrayResponseType = HttpResponse<IInterviewQuestionSet[]>;

@Injectable({ providedIn: 'root' })
export class InterviewQuestionSetService {
  public resourceUrl = SERVER_API_URL + 'api/interview-question-sets';

  constructor(protected http: HttpClient) {}

  create(interviewQuestionSet: IInterviewQuestionSet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interviewQuestionSet);
    return this.http
      .post<IInterviewQuestionSet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(interviewQuestionSet: IInterviewQuestionSet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(interviewQuestionSet);
    return this.http
      .put<IInterviewQuestionSet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInterviewQuestionSet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInterviewQuestionSet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(interviewQuestionSet: IInterviewQuestionSet): IInterviewQuestionSet {
    const copy: IInterviewQuestionSet = Object.assign({}, interviewQuestionSet, {
      time: interviewQuestionSet.time && interviewQuestionSet.time.isValid() ? interviewQuestionSet.time.toJSON() : undefined,
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
      res.body.forEach((interviewQuestionSet: IInterviewQuestionSet) => {
        interviewQuestionSet.time = interviewQuestionSet.time ? moment(interviewQuestionSet.time) : undefined;
      });
    }
    return res;
  }
}
