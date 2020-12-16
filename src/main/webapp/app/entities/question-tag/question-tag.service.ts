import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionTag } from 'app/shared/model/question-tag.model';

type EntityResponseType = HttpResponse<IQuestionTag>;
type EntityArrayResponseType = HttpResponse<IQuestionTag[]>;

@Injectable({ providedIn: 'root' })
export class QuestionTagService {
  public resourceUrl = SERVER_API_URL + 'api/question-tags';

  constructor(protected http: HttpClient) {}

  create(questionTag: IQuestionTag): Observable<EntityResponseType> {
    return this.http.post<IQuestionTag>(this.resourceUrl, questionTag, { observe: 'response' });
  }

  update(questionTag: IQuestionTag): Observable<EntityResponseType> {
    return this.http.put<IQuestionTag>(this.resourceUrl, questionTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
