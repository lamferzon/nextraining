import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TestdiCooper } from 'app/shared/model/testdi-cooper.model';
import { TestdiCooperService } from './testdi-cooper.service';
import { TestdiCooperComponent } from './testdi-cooper.component';
import { TestdiCooperDetailComponent } from './testdi-cooper-detail.component';
import { TestdiCooperUpdateComponent } from './testdi-cooper-update.component';
import { TestdiCooperDeletePopupComponent } from './testdi-cooper-delete-dialog.component';
import { ITestdiCooper } from 'app/shared/model/testdi-cooper.model';

@Injectable({ providedIn: 'root' })
export class TestdiCooperResolve implements Resolve<ITestdiCooper> {
  constructor(private service: TestdiCooperService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITestdiCooper> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TestdiCooper>) => response.ok),
        map((testdiCooper: HttpResponse<TestdiCooper>) => testdiCooper.body)
      );
    }
    return of(new TestdiCooper());
  }
}

export const testdiCooperRoute: Routes = [
  {
    path: '',
    component: TestdiCooperComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiCooper.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TestdiCooperDetailComponent,
    resolve: {
      testdiCooper: TestdiCooperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiCooper.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TestdiCooperUpdateComponent,
    resolve: {
      testdiCooper: TestdiCooperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiCooper.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TestdiCooperUpdateComponent,
    resolve: {
      testdiCooper: TestdiCooperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiCooper.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const testdiCooperPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TestdiCooperDeletePopupComponent,
    resolve: {
      testdiCooper: TestdiCooperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiCooper.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
