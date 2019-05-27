import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpecialista } from 'app/shared/model/specialista.model';

type EntityResponseType = HttpResponse<ISpecialista>;
type EntityArrayResponseType = HttpResponse<ISpecialista[]>;

@Injectable({ providedIn: 'root' })
export class SpecialistaService {
  public resourceUrl = SERVER_API_URL + 'api/specialistas';

  constructor(protected http: HttpClient) {}

  create(specialista: ISpecialista): Observable<EntityResponseType> {
    return this.http.post<ISpecialista>(this.resourceUrl, specialista, { observe: 'response' });
  }

  update(specialista: ISpecialista): Observable<EntityResponseType> {
    return this.http.put<ISpecialista>(this.resourceUrl, specialista, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecialista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecialista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
