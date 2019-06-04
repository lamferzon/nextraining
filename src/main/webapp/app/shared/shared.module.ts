import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NextrainingSharedLibsModule, NextrainingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { SelettoreFilterPipe } from './util/selettore-filter';

@NgModule({
  imports: [NextrainingSharedLibsModule, NextrainingSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, SelettoreFilterPipe],
  entryComponents: [JhiLoginModalComponent],
  exports: [NextrainingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, SelettoreFilterPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingSharedModule {
  static forRoot() {
    return {
      ngModule: NextrainingSharedModule
    };
  }
}
