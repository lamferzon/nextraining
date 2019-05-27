import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TestdiConconi } from 'app/shared/model/testdi-conconi.model';
import { TestdiConconiService } from './testdi-conconi.service';
import { TestdiConconiComponent } from './testdi-conconi.component';
import { TestdiConconiDetailComponent } from './testdi-conconi-detail.component';
import { TestdiConconiUpdateComponent } from './testdi-conconi-update.component';
import { TestdiConconiDeletePopupComponent } from './testdi-conconi-delete-dialog.component';
import { ITestdiConconi } from 'app/shared/model/testdi-conconi.model';

@Injectable({ providedIn: 'root' })
export class TestdiConconiResolve implements Resolve<ITestdiConconi> {
  constructor(private service: TestdiConconiService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITestdiConconi> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TestdiConconi>) => response.ok),
        map((testdiConconi: HttpResponse<TestdiConconi>) => testdiConconi.body)
      );
    }
    return of(new TestdiConconi());
  }
}

export const testdiConconiRoute: Routes = [
  {
    path: '',
    component: TestdiConconiComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiConconi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TestdiConconiDetailComponent,
    resolve: {
      testdiConconi: TestdiConconiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiConconi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TestdiConconiUpdateComponent,
    resolve: {
      testdiConconi: TestdiConconiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiConconi.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TestdiConconiUpdateComponent,
    resolve: {
      testdiConconi: TestdiConconiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiConconi.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const testdiConconiPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TestdiConconiDeletePopupComponent,
    resolve: {
      testdiConconi: TestdiConconiResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'nextrainingApp.testdiConconi.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
