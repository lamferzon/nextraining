import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Allenamento } from 'app/shared/model/allenamento.model';
import { AllenamentoService } from './allenamento.service';
import { AllenamentoComponent } from './allenamento.component';
import { AllenamentoDetailComponent } from './allenamento-detail.component';
import { AllenamentoUpdateComponent } from './allenamento-update.component';
import { AllenamentoDeletePopupComponent } from './allenamento-delete-dialog.component';
import { IAllenamento } from 'app/shared/model/allenamento.model';

@Injectable({ providedIn: 'root' })
export class AllenamentoResolve implements Resolve<IAllenamento> {
  constructor(private service: AllenamentoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllenamento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Allenamento>) => response.ok),
        map((allenamento: HttpResponse<Allenamento>) => allenamento.body)
      );
    }
    return of(new Allenamento());
  }
}

export const allenamentoRoute: Routes = [
  {
    path: '',
    component: AllenamentoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllenamentoDetailComponent,
    resolve: {
      allenamento: AllenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllenamentoUpdateComponent,
    resolve: {
      allenamento: AllenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllenamentoUpdateComponent,
    resolve: {
      allenamento: AllenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allenamentoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllenamentoDeletePopupComponent,
    resolve: {
      allenamento: AllenamentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
