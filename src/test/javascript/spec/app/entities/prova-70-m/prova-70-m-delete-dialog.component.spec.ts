/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { Prova70mDeleteDialogComponent } from 'app/entities/prova-70-m/prova-70-m-delete-dialog.component';
import { Prova70mService } from 'app/entities/prova-70-m/prova-70-m.service';

describe('Component Tests', () => {
  describe('Prova70m Management Delete Component', () => {
    let comp: Prova70mDeleteDialogComponent;
    let fixture: ComponentFixture<Prova70mDeleteDialogComponent>;
    let service: Prova70mService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [Prova70mDeleteDialogComponent]
      })
        .overrideTemplate(Prova70mDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Prova70mDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Prova70mService);
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
