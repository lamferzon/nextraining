/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { AllenamentoUpdateComponent } from 'app/entities/allenamento/allenamento-update.component';
import { AllenamentoService } from 'app/entities/allenamento/allenamento.service';
import { Allenamento } from 'app/shared/model/allenamento.model';

describe('Component Tests', () => {
  describe('Allenamento Management Update Component', () => {
    let comp: AllenamentoUpdateComponent;
    let fixture: ComponentFixture<AllenamentoUpdateComponent>;
    let service: AllenamentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [AllenamentoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllenamentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllenamentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllenamentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Allenamento(123);
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
        const entity = new Allenamento();
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
