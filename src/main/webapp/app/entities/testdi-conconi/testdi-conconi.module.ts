import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NextrainingSharedModule } from 'app/shared';
import {
  TestdiConconiComponent,
  TestdiConconiDetailComponent,
  TestdiConconiUpdateComponent,
  TestdiConconiDeletePopupComponent,
  TestdiConconiDeleteDialogComponent,
  testdiConconiRoute,
  testdiConconiPopupRoute
} from './';

const ENTITY_STATES = [...testdiConconiRoute, ...testdiConconiPopupRoute];

@NgModule({
  imports: [NextrainingSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TestdiConconiComponent,
    TestdiConconiDetailComponent,
    TestdiConconiUpdateComponent,
    TestdiConconiDeleteDialogComponent,
    TestdiConconiDeletePopupComponent
  ],
  entryComponents: [
    TestdiConconiComponent,
    TestdiConconiUpdateComponent,
    TestdiConconiDeleteDialogComponent,
    TestdiConconiDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingTestdiConconiModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
