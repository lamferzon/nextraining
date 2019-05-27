import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  SpecialistaComponent,
  SpecialistaDetailComponent,
  SpecialistaUpdateComponent,
  SpecialistaDeletePopupComponent,
  SpecialistaDeleteDialogComponent,
  specialistaRoute,
  specialistaPopupRoute
} from './';

const ENTITY_STATES = [...specialistaRoute, ...specialistaPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpecialistaComponent,
    SpecialistaDetailComponent,
    SpecialistaUpdateComponent,
    SpecialistaDeleteDialogComponent,
    SpecialistaDeletePopupComponent
  ],
  entryComponents: [SpecialistaComponent, SpecialistaUpdateComponent, SpecialistaDeleteDialogComponent, SpecialistaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingSpecialistaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
