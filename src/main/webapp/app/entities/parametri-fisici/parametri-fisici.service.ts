import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParametriFisici } from 'app/shared/model/parametri-fisici.model';

type EntityResponseType = HttpResponse<IParametriFisici>;
type EntityArrayResponseType = HttpResponse<IParametriFisici[]>;

@Injectable({ providedIn: 'root' })
export class ParametriFisiciService {
  public resourceUrl = SERVER_API_URL + 'api/parametri-fisicis';

  constructor(protected http: HttpClient) {}

  create(parametriFisici: IParametriFisici): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parametriFisici);
    return this.http
      .post<IParametriFisici>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(parametriFisici: IParametriFisici): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parametriFisici);
    return this.http
      .put<IParametriFisici>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParametriFisici>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParametriFisici[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(parametriFisici: IParametriFisici): IParametriFisici {
    const copy: IParametriFisici = Object.assign({}, parametriFisici, {
      dataRivelazione:
        parametriFisici.dataRivelazione != null && parametriFisici.dataRivelazione.isValid()
          ? parametriFisici.dataRivelazione.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataRivelazione = res.body.dataRivelazione != null ? moment(res.body.dataRivelazione) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((parametriFisici: IParametriFisici) => {
        parametriFisici.dataRivelazione = parametriFisici.dataRivelazione != null ? moment(parametriFisici.dataRivelazione) : null;
      });
    }
    return res;
  }
}
