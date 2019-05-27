/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { TestdiConconiDeleteDialogComponent } from 'app/entities/testdi-conconi/testdi-conconi-delete-dialog.component';
import { TestdiConconiService } from 'app/entities/testdi-conconi/testdi-conconi.service';

describe('Component Tests', () => {
  describe('TestdiConconi Management Delete Component', () => {
    let comp: TestdiConconiDeleteDialogComponent;
    let fixture: ComponentFixture<TestdiConconiDeleteDialogComponent>;
    let service: TestdiConconiService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [TestdiConconiDeleteDialogComponent]
      })
        .overrideTemplate(TestdiConconiDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestdiConconiDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestdiConconiService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
