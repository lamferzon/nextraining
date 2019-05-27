/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { CalciatoreUpdateComponent } from 'app/entities/calciatore/calciatore-update.component';
import { CalciatoreService } from 'app/entities/calciatore/calciatore.service';
import { Calciatore } from 'app/shared/model/calciatore.model';

describe('Component Tests', () => {
  describe('Calciatore Management Update Component', () => {
    let comp: CalciatoreUpdateComponent;
    let fixture: ComponentFixture<CalciatoreUpdateComponent>;
    let service: CalciatoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [CalciatoreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CalciatoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalciatoreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalciatoreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Calciatore(123);
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
        const entity = new Calciatore();
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
