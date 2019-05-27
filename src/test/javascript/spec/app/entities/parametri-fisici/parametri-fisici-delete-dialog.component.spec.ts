/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { ParametriFisiciDeleteDialogComponent } from 'app/entities/parametri-fisici/parametri-fisici-delete-dialog.component';
import { ParametriFisiciService } from 'app/entities/parametri-fisici/parametri-fisici.service';

describe('Component Tests', () => {
  describe('ParametriFisici Management Delete Component', () => {
    let comp: ParametriFisiciDeleteDialogComponent;
    let fixture: ComponentFixture<ParametriFisiciDeleteDialogComponent>;
    let service: ParametriFisiciService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [ParametriFisiciDeleteDialogComponent]
      })
        .overrideTemplate(ParametriFisiciDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParametriFisiciDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParametriFisiciService);
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
