/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { SpecialistaDeleteDialogComponent } from 'app/entities/specialista/specialista-delete-dialog.component';
import { SpecialistaService } from 'app/entities/specialista/specialista.service';

describe('Component Tests', () => {
  describe('Specialista Management Delete Component', () => {
    let comp: SpecialistaDeleteDialogComponent;
    let fixture: ComponentFixture<SpecialistaDeleteDialogComponent>;
    let service: SpecialistaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [SpecialistaDeleteDialogComponent]
      })
        .overrideTemplate(SpecialistaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialistaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialistaService);
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
