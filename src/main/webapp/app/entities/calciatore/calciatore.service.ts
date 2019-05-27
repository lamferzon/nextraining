import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICalciatore } from 'app/shared/model/calciatore.model';

type EntityResponseType = HttpResponse<ICalciatore>;
type EntityArrayResponseType = HttpResponse<ICalciatore[]>;

@Injectable({ providedIn: 'root' })
export class CalciatoreService {
  public resourceUrl = SERVER_API_URL + 'api/calciatores';

  constructor(protected http: HttpClient) {}

  create(calciatore: ICalciatore): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calciatore);
    return this.http
      .post<ICalciatore>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(calciatore: ICalciatore): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(calciatore);
    return this.http
      .put<ICalciatore>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICalciatore>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICalciatore[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(calciatore: ICalciatore): ICalciatore {
    const copy: ICalciatore = Object.assign({}, calciatore, {
      dataNascita: calciatore.dataNascita != null && calciatore.dataNascita.isValid() ? calciatore.dataNascita.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNascita = res.body.dataNascita != null ? moment(res.body.dataNascita) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((calciatore: ICalciatore) => {
        calciatore.dataNascita = calciatore.dataNascita != null ? moment(calciatore.dataNascita) : null;
      });
    }
    return res;
  }
}
