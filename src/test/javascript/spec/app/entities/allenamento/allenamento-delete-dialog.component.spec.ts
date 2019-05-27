/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { AllenamentoDeleteDialogComponent } from 'app/entities/allenamento/allenamento-delete-dialog.component';
import { AllenamentoService } from 'app/entities/allenamento/allenamento.service';

describe('Component Tests', () => {
  describe('Allenamento Management Delete Component', () => {
    let comp: AllenamentoDeleteDialogComponent;
    let fixture: ComponentFixture<AllenamentoDeleteDialogComponent>;
    let service: AllenamentoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [AllenamentoDeleteDialogComponent]
      })
        .overrideTemplate(AllenamentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllenamentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllenamentoService);
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
