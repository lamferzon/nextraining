import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prova1500m } from 'app/shared/model/prova-1500-m.model';
import { Prova1500mService } from './prova-1500-m.service';
import { Prova1500mComponent } from './prova-1500-m.component';
import { Prova1500mDetailComponent } from './prova-1500-m-detail.component';
import { Prova1500mUpdateComponent } from './prova-1500-m-update.component';
import { Prova1500mDeletePopupComponent } from './prova-1500-m-delete-dialog.component';
import { IProva1500m } from 'app/shared/model/prova-1500-m.model';

@Injectable({ providedIn: 'root' })
export class Prova1500mResolve implements Resolve<IProva1500m> {
  constructor(private service: Prova1500mService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProva1500m> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Prova1500m>) => response.ok),
        map((prova1500m: HttpResponse<Prova1500m>) => prova1500m.body)
      );
    }
    return of(new Prova1500m());
  }
}

export const prova1500mRoute: Routes = [
  {
    path: '',
    component: Prova1500mComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova1500m.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Prova1500mDetailComponent,
    resolve: {
      prova1500m: Prova1500mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova1500m.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Prova1500mUpdateComponent,
    resolve: {
      prova1500m: Prova1500mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova1500m.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Prova1500mUpdateComponent,
    resolve: {
      prova1500m: Prova1500mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova1500m.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prova1500mPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Prova1500mDeletePopupComponent,
    resolve: {
      prova1500m: Prova1500mResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.prova1500m.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
