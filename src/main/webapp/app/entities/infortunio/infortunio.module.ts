import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  InfortunioComponent,
  InfortunioDetailComponent,
  InfortunioUpdateComponent,
  InfortunioDeletePopupComponent,
  InfortunioDeleteDialogComponent,
  infortunioRoute,
  infortunioPopupRoute
} from './';

const ENTITY_STATES = [...infortunioRoute, ...infortunioPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InfortunioComponent,
    InfortunioDetailComponent,
    InfortunioUpdateComponent,
    InfortunioDeleteDialogComponent,
    InfortunioDeletePopupComponent
  ],
  entryComponents: [InfortunioComponent, InfortunioUpdateComponent, InfortunioDeleteDialogComponent, InfortunioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingInfortunioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
