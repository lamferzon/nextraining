import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  TestdiCooperComponent,
  TestdiCooperDetailComponent,
  TestdiCooperUpdateComponent,
  TestdiCooperDeletePopupComponent,
  TestdiCooperDeleteDialogComponent,
  testdiCooperRoute,
  testdiCooperPopupRoute
} from './';

const ENTITY_STATES = [...testdiCooperRoute, ...testdiCooperPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TestdiCooperComponent,
    TestdiCooperDetailComponent,
    TestdiCooperUpdateComponent,
    TestdiCooperDeleteDialogComponent,
    TestdiCooperDeletePopupComponent
  ],
  entryComponents: [
    TestdiCooperComponent,
    TestdiCooperUpdateComponent,
    TestdiCooperDeleteDialogComponent,
    TestdiCooperDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingTestdiCooperModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
