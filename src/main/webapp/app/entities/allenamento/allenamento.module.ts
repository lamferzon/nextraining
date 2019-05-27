import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  AllenamentoComponent,
  AllenamentoDetailComponent,
  AllenamentoUpdateComponent,
  AllenamentoDeletePopupComponent,
  AllenamentoDeleteDialogComponent,
  allenamentoRoute,
  allenamentoPopupRoute
} from './';

const ENTITY_STATES = [...allenamentoRoute, ...allenamentoPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllenamentoComponent,
    AllenamentoDetailComponent,
    AllenamentoUpdateComponent,
    AllenamentoDeleteDialogComponent,
    AllenamentoDeletePopupComponent
  ],
  entryComponents: [AllenamentoComponent, AllenamentoUpdateComponent, AllenamentoDeleteDialogComponent, AllenamentoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingAllenamentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
