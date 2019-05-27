import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NextrainingSharedLibsModule, NextrainingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [NextrainingSharedLibsModule, NextrainingSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [NextrainingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NextrainingSharedModule {
  static forRoot() {
    return {
      ngModule: NextrainingSharedModule
    };
  }
}
