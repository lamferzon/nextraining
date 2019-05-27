import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParametriFisici } from 'app/shared/model/parametri-fisici.model';
import { ParametriFisiciService } from './parametri-fisici.service';
import { ParametriFisiciComponent } from './parametri-fisici.component';
import { ParametriFisiciDetailComponent } from './parametri-fisici-detail.component';
import { ParametriFisiciUpdateComponent } from './parametri-fisici-update.component';
import { ParametriFisiciDeletePopupComponent } from './parametri-fisici-delete-dialog.component';
import { IParametriFisici } from 'app/shared/model/parametri-fisici.model';

@Injectable({ providedIn: 'root' })
export class ParametriFisiciResolve implements Resolve<IParametriFisici> {
  constructor(private service: ParametriFisiciService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParametriFisici> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ParametriFisici>) => response.ok),
        map((parametriFisici: HttpResponse<ParametriFisici>) => parametriFisici.body)
      );
    }
    return of(new ParametriFisici());
  }
}

export const parametriFisiciRoute: Routes = [
  {
    path: '',
    component: ParametriFisiciComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.parametriFisici.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParametriFisiciDetailComponent,
    resolve: {
      parametriFisici: ParametriFisiciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.parametriFisici.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParametriFisiciUpdateComponent,
    resolve: {
      parametriFisici: ParametriFisiciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.parametriFisici.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParametriFisiciUpdateComponent,
    resolve: {
      parametriFisici: ParametriFisiciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.parametriFisici.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const parametriFisiciPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParametriFisiciDeletePopupComponent,
    resolve: {
      parametriFisici: ParametriFisiciResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.parametriFisici.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
