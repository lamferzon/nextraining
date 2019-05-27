import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInfortunio } from 'app/shared/model/infortunio.model';

type EntityResponseType = HttpResponse<IInfortunio>;
type EntityArrayResponseType = HttpResponse<IInfortunio[]>;

@Injectable({ providedIn: 'root' })
export class InfortunioService {
  public resourceUrl = SERVER_API_URL + 'api/infortunios';

  constructor(protected http: HttpClient) {}

  create(infortunio: IInfortunio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infortunio);
    return this.http
      .post<IInfortunio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(infortunio: IInfortunio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infortunio);
    return this.http
      .put<IInfortunio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInfortunio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInfortunio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(infortunio: IInfortunio): IInfortunio {
    const copy: IInfortunio = Object.assign({}, infortunio, {
      dataInizio: infortunio.dataInizio != null && infortunio.dataInizio.isValid() ? infortunio.dataInizio.format(DATE_FORMAT) : null,
      dataFine: infortunio.dataFine != null && infortunio.dataFine.isValid() ? infortunio.dataFine.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInizio = res.body.dataInizio != null ? moment(res.body.dataInizio) : null;
      res.body.dataFine = res.body.dataFine != null ? moment(res.body.dataFine) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((infortunio: IInfortunio) => {
        infortunio.dataInizio = infortunio.dataInizio != null ? moment(infortunio.dataInizio) : null;
        infortunio.dataFine = infortunio.dataFine != null ? moment(infortunio.dataFine) : null;
      });
    }
    return res;
  }
}
