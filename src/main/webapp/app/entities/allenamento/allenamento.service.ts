import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAllenamento } from 'app/shared/model/allenamento.model';

type EntityResponseType = HttpResponse<IAllenamento>;
type EntityArrayResponseType = HttpResponse<IAllenamento[]>;

@Injectable({ providedIn: 'root' })
export class AllenamentoService {
  public resourceUrl = SERVER_API_URL + 'api/allenamentos';

  constructor(protected http: HttpClient) {}

  create(allenamento: IAllenamento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(allenamento);
    return this.http
      .post<IAllenamento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(allenamento: IAllenamento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(allenamento);
    return this.http
      .put<IAllenamento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAllenamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAllenamento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(allenamento: IAllenamento): IAllenamento {
    const copy: IAllenamento = Object.assign({}, allenamento, {
      dataSvolgimento:
        allenamento.dataSvolgimento != null && allenamento.dataSvolgimento.isValid()
          ? allenamento.dataSvolgimento.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataSvolgimento = res.body.dataSvolgimento != null ? moment(res.body.dataSvolgimento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((allenamento: IAllenamento) => {
        allenamento.dataSvolgimento = allenamento.dataSvolgimento != null ? moment(allenamento.dataSvolgimento) : null;
      });
    }
    return res;
  }
}
