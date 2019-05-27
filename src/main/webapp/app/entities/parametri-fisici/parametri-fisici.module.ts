import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  ParametriFisiciComponent,
  ParametriFisiciDetailComponent,
  ParametriFisiciUpdateComponent,
  ParametriFisiciDeletePopupComponent,
  ParametriFisiciDeleteDialogComponent,
  parametriFisiciRoute,
  parametriFisiciPopupRoute
} from './';

const ENTITY_STATES = [...parametriFisiciRoute, ...parametriFisiciPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ParametriFisiciComponent,
    ParametriFisiciDetailComponent,
    ParametriFisiciUpdateComponent,
    ParametriFisiciDeleteDialogComponent,
    ParametriFisiciDeletePopupComponent
  ],
  entryComponents: [
    ParametriFisiciComponent,
    ParametriFisiciUpdateComponent,
    ParametriFisiciDeleteDialogComponent,
    ParametriFisiciDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingParametriFisiciModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
