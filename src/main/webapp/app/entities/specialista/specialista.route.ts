import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Specialista } from 'app/shared/model/specialista.model';
import { SpecialistaService } from './specialista.service';
import { SpecialistaComponent } from './specialista.component';
import { SpecialistaDetailComponent } from './specialista-detail.component';
import { SpecialistaUpdateComponent } from './specialista-update.component';
import { SpecialistaDeletePopupComponent } from './specialista-delete-dialog.component';
import { ISpecialista } from 'app/shared/model/specialista.model';

@Injectable({ providedIn: 'root' })
export class SpecialistaResolve implements Resolve<ISpecialista> {
  constructor(private service: SpecialistaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpecialista> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Specialista>) => response.ok),
        map((specialista: HttpResponse<Specialista>) => specialista.body)
      );
    }
    return of(new Specialista());
  }
}

export const specialistaRoute: Routes = [
  {
    path: '',
    component: SpecialistaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.specialista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpecialistaDetailComponent,
    resolve: {
      specialista: SpecialistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.specialista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpecialistaUpdateComponent,
    resolve: {
      specialista: SpecialistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.specialista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpecialistaUpdateComponent,
    resolve: {
      specialista: SpecialistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.specialista.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const specialistaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpecialistaDeletePopupComponent,
    resolve: {
      specialista: SpecialistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.specialista.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
