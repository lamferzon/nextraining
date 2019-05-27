import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  CalciatoreComponent,
  CalciatoreDetailComponent,
  CalciatoreUpdateComponent,
  CalciatoreDeletePopupComponent,
  CalciatoreDeleteDialogComponent,
  calciatoreRoute,
  calciatorePopupRoute
} from './';

const ENTITY_STATES = [...calciatoreRoute, ...calciatorePopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CalciatoreComponent,
    CalciatoreDetailComponent,
    CalciatoreUpdateComponent,
    CalciatoreDeleteDialogComponent,
    CalciatoreDeletePopupComponent
  ],
  entryComponents: [CalciatoreComponent, CalciatoreUpdateComponent, CalciatoreDeleteDialogComponent, CalciatoreDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingCalciatoreModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
