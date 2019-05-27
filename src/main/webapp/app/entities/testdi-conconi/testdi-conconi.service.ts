import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITestdiConconi } from 'app/shared/model/testdi-conconi.model';

type EntityResponseType = HttpResponse<ITestdiConconi>;
type EntityArrayResponseType = HttpResponse<ITestdiConconi[]>;

@Injectable({ providedIn: 'root' })
export class TestdiConconiService {
  public resourceUrl = SERVER_API_URL + 'api/testdi-conconis';

  constructor(protected http: HttpClient) {}

  create(testdiConconi: ITestdiConconi): Observable<EntityResponseType> {
    return this.http.post<ITestdiConconi>(this.resourceUrl, testdiConconi, { observe: 'response' });
  }

  update(testdiConconi: ITestdiConconi): Observable<EntityResponseType> {
    return this.http.put<ITestdiConconi>(this.resourceUrl, testdiConconi, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITestdiConconi>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITestdiConconi[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
