import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProva1500m } from 'app/shared/model/prova-1500-m.model';

type EntityResponseType = HttpResponse<IProva1500m>;
type EntityArrayResponseType = HttpResponse<IProva1500m[]>;

@Injectable({ providedIn: 'root' })
export class Prova1500mService {
  public resourceUrl = SERVER_API_URL + 'api/prova-1500-ms';

  constructor(protected http: HttpClient) {}

  create(prova1500m: IProva1500m): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prova1500m);
    return this.http
      .post<IProva1500m>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(prova1500m: IProva1500m): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prova1500m);
    return this.http
      .put<IProva1500m>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProva1500m>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProva1500m[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(prova1500m: IProva1500m): IProva1500m {
    const copy: IProva1500m = Object.assign({}, prova1500m, {
      dataProva: prova1500m.dataProva != null && prova1500m.dataProva.isValid() ? prova1500m.dataProva.format(DATE_FORMAT) : null
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
      res.body.forEach((prova1500m: IProva1500m) => {
        prova1500m.dataProva = prova1500m.dataProva != null ? moment(prova1500m.dataProva) : null;
      });
    }
    return res;
  }
}
