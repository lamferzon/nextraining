import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  AllenamentoAggiuntivoComponent,
  AllenamentoAggiuntivoDetailComponent,
  AllenamentoAggiuntivoUpdateComponent,
  AllenamentoAggiuntivoDeletePopupComponent,
  AllenamentoAggiuntivoDeleteDialogComponent,
  allenamentoAggiuntivoRoute,
  allenamentoAggiuntivoPopupRoute
} from './';

const ENTITY_STATES = [...allenamentoAggiuntivoRoute, ...allenamentoAggiuntivoPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AllenamentoAggiuntivoComponent,
    AllenamentoAggiuntivoDetailComponent,
    AllenamentoAggiuntivoUpdateComponent,
    AllenamentoAggiuntivoDeleteDialogComponent,
    AllenamentoAggiuntivoDeletePopupComponent
  ],
  entryComponents: [
    AllenamentoAggiuntivoComponent,
    AllenamentoAggiuntivoUpdateComponent,
    AllenamentoAggiuntivoDeleteDialogComponent,
    AllenamentoAggiuntivoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingAllenamentoAggiuntivoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
