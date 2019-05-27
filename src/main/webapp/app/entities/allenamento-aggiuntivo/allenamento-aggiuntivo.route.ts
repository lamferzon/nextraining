import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';
import { AllenamentoAggiuntivoService } from './allenamento-aggiuntivo.service';
import { AllenamentoAggiuntivoComponent } from './allenamento-aggiuntivo.component';
import { AllenamentoAggiuntivoDetailComponent } from './allenamento-aggiuntivo-detail.component';
import { AllenamentoAggiuntivoUpdateComponent } from './allenamento-aggiuntivo-update.component';
import { AllenamentoAggiuntivoDeletePopupComponent } from './allenamento-aggiuntivo-delete-dialog.component';
import { IAllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';

@Injectable({ providedIn: 'root' })
export class AllenamentoAggiuntivoResolve implements Resolve<IAllenamentoAggiuntivo> {
  constructor(private service: AllenamentoAggiuntivoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAllenamentoAggiuntivo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AllenamentoAggiuntivo>) => response.ok),
        map((allenamentoAggiuntivo: HttpResponse<AllenamentoAggiuntivo>) => allenamentoAggiuntivo.body)
      );
    }
    return of(new AllenamentoAggiuntivo());
  }
}

export const allenamentoAggiuntivoRoute: Routes = [
  {
    path: '',
    component: AllenamentoAggiuntivoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamentoAggiuntivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AllenamentoAggiuntivoDetailComponent,
    resolve: {
      allenamentoAggiuntivo: AllenamentoAggiuntivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamentoAggiuntivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AllenamentoAggiuntivoUpdateComponent,
    resolve: {
      allenamentoAggiuntivo: AllenamentoAggiuntivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamentoAggiuntivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AllenamentoAggiuntivoUpdateComponent,
    resolve: {
      allenamentoAggiuntivo: AllenamentoAggiuntivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamentoAggiuntivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const allenamentoAggiuntivoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AllenamentoAggiuntivoDeletePopupComponent,
    resolve: {
      allenamentoAggiuntivo: AllenamentoAggiuntivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.allenamentoAggiuntivo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
