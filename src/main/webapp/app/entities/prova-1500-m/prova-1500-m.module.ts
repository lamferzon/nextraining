import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  Prova1500mComponent,
  Prova1500mDetailComponent,
  Prova1500mUpdateComponent,
  Prova1500mDeletePopupComponent,
  Prova1500mDeleteDialogComponent,
  prova1500mRoute,
  prova1500mPopupRoute
} from './';

const ENTITY_STATES = [...prova1500mRoute, ...prova1500mPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Prova1500mComponent,
    Prova1500mDetailComponent,
    Prova1500mUpdateComponent,
    Prova1500mDeleteDialogComponent,
    Prova1500mDeletePopupComponent
  ],
  entryComponents: [Prova1500mComponent, Prova1500mUpdateComponent, Prova1500mDeleteDialogComponent, Prova1500mDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingProva1500mModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
