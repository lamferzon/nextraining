/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NextrainingTestModule } from '../../../test.module';
import { AllenamentoAggiuntivoDeleteDialogComponent } from 'app/entities/allenamento-aggiuntivo/allenamento-aggiuntivo-delete-dialog.component';
import { AllenamentoAggiuntivoService } from 'app/entities/allenamento-aggiuntivo/allenamento-aggiuntivo.service';

describe('Component Tests', () => {
  describe('AllenamentoAggiuntivo Management Delete Component', () => {
    let comp: AllenamentoAggiuntivoDeleteDialogComponent;
    let fixture: ComponentFixture<AllenamentoAggiuntivoDeleteDialogComponent>;
    let service: AllenamentoAggiuntivoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [AllenamentoAggiuntivoDeleteDialogComponent]
      })
        .overrideTemplate(AllenamentoAggiuntivoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllenamentoAggiuntivoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllenamentoAggiuntivoService);
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
