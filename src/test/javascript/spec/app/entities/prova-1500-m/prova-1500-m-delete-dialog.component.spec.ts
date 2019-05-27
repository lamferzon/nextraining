/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { Prova1500mDeleteDialogComponent } from 'app/entities/prova-1500-m/prova-1500-m-delete-dialog.component';
import { Prova1500mService } from 'app/entities/prova-1500-m/prova-1500-m.service';

describe('Component Tests', () => {
  describe('Prova1500m Management Delete Component', () => {
    let comp: Prova1500mDeleteDialogComponent;
    let fixture: ComponentFixture<Prova1500mDeleteDialogComponent>;
    let service: Prova1500mService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [Prova1500mDeleteDialogComponent]
      })
        .overrideTemplate(Prova1500mDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Prova1500mDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Prova1500mService);
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
