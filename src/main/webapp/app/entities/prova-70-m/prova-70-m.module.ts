import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  Prova70mComponent,
  Prova70mDetailComponent,
  Prova70mUpdateComponent,
  Prova70mDeletePopupComponent,
  Prova70mDeleteDialogComponent,
  prova70mRoute,
  prova70mPopupRoute
} from './';

const ENTITY_STATES = [...prova70mRoute, ...prova70mPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Prova70mComponent,
    Prova70mDetailComponent,
    Prova70mUpdateComponent,
    Prova70mDeleteDialogComponent,
    Prova70mDeletePopupComponent
  ],
  entryComponents: [Prova70mComponent, Prova70mUpdateComponent, Prova70mDeleteDialogComponent, Prova70mDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingProva70mModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
