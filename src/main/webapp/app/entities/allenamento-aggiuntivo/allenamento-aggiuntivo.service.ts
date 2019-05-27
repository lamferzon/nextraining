import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';

type EntityResponseType = HttpResponse<IAllenamentoAggiuntivo>;
type EntityArrayResponseType = HttpResponse<IAllenamentoAggiuntivo[]>;

@Injectable({ providedIn: 'root' })
export class AllenamentoAggiuntivoService {
  public resourceUrl = SERVER_API_URL + 'api/allenamento-aggiuntivos';

  constructor(protected http: HttpClient) {}

  create(allenamentoAggiuntivo: IAllenamentoAggiuntivo): Observable<EntityResponseType> {
    return this.http.post<IAllenamentoAggiuntivo>(this.resourceUrl, allenamentoAggiuntivo, { observe: 'response' });
  }

  update(allenamentoAggiuntivo: IAllenamentoAggiuntivo): Observable<EntityResponseType> {
    return this.http.put<IAllenamentoAggiuntivo>(this.resourceUrl, allenamentoAggiuntivo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAllenamentoAggiuntivo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAllenamentoAggiuntivo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
