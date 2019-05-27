import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Infortunio } from 'app/shared/model/infortunio.model';
import { InfortunioService } from './infortunio.service';
import { InfortunioComponent } from './infortunio.component';
import { InfortunioDetailComponent } from './infortunio-detail.component';
import { InfortunioUpdateComponent } from './infortunio-update.component';
import { InfortunioDeletePopupComponent } from './infortunio-delete-dialog.component';
import { IInfortunio } from 'app/shared/model/infortunio.model';

@Injectable({ providedIn: 'root' })
export class InfortunioResolve implements Resolve<IInfortunio> {
  constructor(private service: InfortunioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInfortunio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Infortunio>) => response.ok),
        map((infortunio: HttpResponse<Infortunio>) => infortunio.body)
      );
    }
    return of(new Infortunio());
  }
}

export const infortunioRoute: Routes = [
  {
    path: '',
    component: InfortunioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.infortunio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InfortunioDetailComponent,
    resolve: {
      infortunio: InfortunioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.infortunio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InfortunioUpdateComponent,
    resolve: {
      infortunio: InfortunioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.infortunio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InfortunioUpdateComponent,
    resolve: {
      infortunio: InfortunioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.infortunio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const infortunioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InfortunioDeletePopupComponent,
    resolve: {
      infortunio: InfortunioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.infortunio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
