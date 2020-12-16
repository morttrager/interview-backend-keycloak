import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionSet } from 'app/shared/model/question-set.model';

type EntityResponseType = HttpResponse<IQuestionSet>;
type EntityArrayResponseType = HttpResponse<IQuestionSet[]>;

@Injectable({ providedIn: 'root' })
export class QuestionSetService {
  public resourceUrl = SERVER_API_URL + 'api/question-sets';

  constructor(protected http: HttpClient) {}

  create(questionSet: IQuestionSet): Observable<EntityResponseType> {
    return this.http.post<IQuestionSet>(this.resourceUrl, questionSet, { observe: 'response' });
  }

  update(questionSet: IQuestionSet): Observable<EntityResponseType> {
    return this.http.put<IQuestionSet>(this.resourceUrl, questionSet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionSet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionSet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
