/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { AllenamentoAggiuntivoUpdateComponent } from 'app/entities/allenamento-aggiuntivo/allenamento-aggiuntivo-update.component';
import { AllenamentoAggiuntivoService } from 'app/entities/allenamento-aggiuntivo/allenamento-aggiuntivo.service';
import { AllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';

describe('Component Tests', () => {
  describe('AllenamentoAggiuntivo Management Update Component', () => {
    let comp: AllenamentoAggiuntivoUpdateComponent;
    let fixture: ComponentFixture<AllenamentoAggiuntivoUpdateComponent>;
    let service: AllenamentoAggiuntivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [AllenamentoAggiuntivoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllenamentoAggiuntivoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllenamentoAggiuntivoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllenamentoAggiuntivoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllenamentoAggiuntivo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllenamentoAggiuntivo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
