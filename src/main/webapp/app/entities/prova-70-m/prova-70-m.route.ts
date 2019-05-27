import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prova70m } from 'app/shared/model/prova-70-m.model';
import { Prova70mService } from './prova-70-m.service';
import { Prova70mComponent } from './prova-70-m.component';
import { Prova70mDetailComponent } from './prova-70-m-detail.component';
import { Prova70mUpdateComponent } from './prova-70-m-update.component';
import { Prova70mDeletePopupComponent } from './prova-70-m-delete-dialog.component';
import { IProva70m } from 'app/shared/model/prova-70-m.model';

@Injectable({ providedIn: 'root' })
export class Prova70mResolve implements Resolve<IProva70m> {
  constructor(private service: Prova70mService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProva70m> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Prova70m>) => response.ok),
        map((prova70m: HttpResponse<Prova70m>) => prova70m.body)
      );
    }
    return of(new Prova70m());
  }
}

export const prova70mRoute: Routes = [
  {
    path: '',
    component: Prova70mComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova70m.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Prova70mDetailComponent,
    resolve: {
      prova70m: Prova70mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova70m.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Prova70mUpdateComponent,
    resolve: {
      prova70m: Prova70mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova70m.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Prova70mUpdateComponent,
    resolve: {
      prova70m: Prova70mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova70m.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prova70mPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Prova70mDeletePopupComponent,
    resolve: {
      prova70m: Prova70mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova70m.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
