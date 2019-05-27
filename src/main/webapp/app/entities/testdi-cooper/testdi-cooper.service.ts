import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITestdiCooper } from 'app/shared/model/testdi-cooper.model';

type EntityResponseType = HttpResponse<ITestdiCooper>;
type EntityArrayResponseType = HttpResponse<ITestdiCooper[]>;

@Injectable({ providedIn: 'root' })
export class TestdiCooperService {
  public resourceUrl = SERVER_API_URL + 'api/testdi-coopers';

  constructor(protected http: HttpClient) {}

  create(testdiCooper: ITestdiCooper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testdiCooper);
    return this.http
      .post<ITestdiCooper>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testdiCooper: ITestdiCooper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testdiCooper);
    return this.http
      .put<ITestdiCooper>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITestdiCooper>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestdiCooper[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(testdiCooper: ITestdiCooper): ITestdiCooper {
    const copy: ITestdiCooper = Object.assign({}, testdiCooper, {
      dataTest: testdiCooper.dataTest != null && testdiCooper.dataTest.isValid() ? testdiCooper.dataTest.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataTest = res.body.dataTest != null ? moment(res.body.dataTest) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((testdiCooper: ITestdiCooper) => {
        testdiCooper.dataTest = testdiCooper.dataTest != null ? moment(testdiCooper.dataTest) : null;
      });
    }
    return res;
  }
}
