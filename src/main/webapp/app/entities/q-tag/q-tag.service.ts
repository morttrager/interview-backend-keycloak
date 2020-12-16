import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQTag } from 'app/shared/model/q-tag.model';

type EntityResponseType = HttpResponse<IQTag>;
type EntityArrayResponseType = HttpResponse<IQTag[]>;

@Injectable({ providedIn: 'root' })
export class QTagService {
  public resourceUrl = SERVER_API_URL + 'api/q-tags';

  constructor(protected http: HttpClient) {}

  create(qTag: IQTag): Observable<EntityResponseType> {
    return this.http.post<IQTag>(this.resourceUrl, qTag, { observe: 'response' });
  }

  update(qTag: IQTag): Observable<EntityResponseType> {
    return this.http.put<IQTag>(this.resourceUrl, qTag, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQTag>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQTag[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
