import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInterviewee } from 'app/shared/model/interviewee.model';

type EntityResponseType = HttpResponse<IInterviewee>;
type EntityArrayResponseType = HttpResponse<IInterviewee[]>;

@Injectable({ providedIn: 'root' })
export class IntervieweeService {
  public resourceUrl = SERVER_API_URL + 'api/interviewees';

  constructor(protected http: HttpClient) {}

  create(interviewee: IInterviewee): Observable<EntityResponseType> {
    return this.http.post<IInterviewee>(this.resourceUrl, interviewee, { observe: 'response' });
  }

  update(interviewee: IInterviewee): Observable<EntityResponseType> {
    return this.http.put<IInterviewee>(this.resourceUrl, interviewee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInterviewee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInterviewee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
