import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Calciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from './calciatore.service';
import { CalciatoreComponent } from './calciatore.component';
import { CalciatoreDetailComponent } from './calciatore-detail.component';
import { CalciatoreUpdateComponent } from './calciatore-update.component';
import { CalciatoreDeletePopupComponent } from './calciatore-delete-dialog.component';
import { ICalciatore } from 'app/shared/model/calciatore.model';

@Injectable({ providedIn: 'root' })
export class CalciatoreResolve implements Resolve<ICalciatore> {
  constructor(private service: CalciatoreService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICalciatore> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Calciatore>) => response.ok),
        map((calciatore: HttpResponse<Calciatore>) => calciatore.body)
      );
    }
    return of(new Calciatore());
  }
}

export const calciatoreRoute: Routes = [
  {
    path: '',
    component: CalciatoreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.calciatore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CalciatoreDetailComponent,
    resolve: {
      calciatore: CalciatoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.calciatore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CalciatoreUpdateComponent,
    resolve: {
      calciatore: CalciatoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.calciatore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CalciatoreUpdateComponent,
    resolve: {
      calciatore: CalciatoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.calciatore.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const calciatorePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CalciatoreDeletePopupComponent,
    resolve: {
      calciatore: CalciatoreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.calciatore.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
