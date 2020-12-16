import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionSetTag } from 'app/shared/model/question-set-tag.model';

type EntityResponseType = HttpResponse<IQuestionSetTag>;
type EntityArrayResponseType = HttpResponse<IQuestionSetTag[]>;

@Injectable({ providedIn: 'root' })
export class QuestionSetTagService {
  public resourceUrl = SERVER_API_URL + 'api/question-set-tags';

  constructor(protected http: HttpClient) {}

  create(questionSetTag: IQuestionSetTag): Observable<EntityResponseType> {
    return this.http.post<IQuestionSetTag>(this.resourceUrl, questionSetTag, { observe: 'response' });
  }

  update(questionSetTag: IQuestionSetTag): Observable<EntityResponseType> {
    return this.http.put<IQuestionSetTag>(this.resourceUrl, questionSetTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionSetTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionSetTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
