/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { CalciatoreDeleteDialogComponent } from 'app/entities/calciatore/calciatore-delete-dialog.component';
import { CalciatoreService } from 'app/entities/calciatore/calciatore.service';

describe('Component Tests', () => {
  describe('Calciatore Management Delete Component', () => {
    let comp: CalciatoreDeleteDialogComponent;
    let fixture: ComponentFixture<CalciatoreDeleteDialogComponent>;
    let service: CalciatoreService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [CalciatoreDeleteDialogComponent]
      })
        .overrideTemplate(CalciatoreDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalciatoreDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalciatoreService);
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
