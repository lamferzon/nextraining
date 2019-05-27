import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProva70m } from 'app/shared/model/prova-70-m.model';

type EntityResponseType = HttpResponse<IProva70m>;
type EntityArrayResponseType = HttpResponse<IProva70m[]>;

@Injectable({ providedIn: 'root' })
export class Prova70mService {
  public resourceUrl = SERVER_API_URL + 'api/prova-70-ms';

  constructor(protected http: HttpClient) {}

  create(prova70m: IProva70m): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prova70m);
    return this.http
      .post<IProva70m>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(prova70m: IProva70m): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prova70m);
    return this.http
      .put<IProva70m>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProva70m>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProva70m[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(prova70m: IProva70m): IProva70m {
    const copy: IProva70m = Object.assign({}, prova70m, {
      dataProva: prova70m.dataProva != null && prova70m.dataProva.isValid() ? prova70m.dataProva.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataProva = res.body.dataProva != null ? moment(res.body.dataProva) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((prova70m: IProva70m) => {
        prova70m.dataProva = prova70m.dataProva != null ? moment(prova70m.dataProva) : null;
      });
    }
    return res;
  }
}
