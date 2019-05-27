/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { SpecialistaUpdateComponent } from 'app/entities/specialista/specialista-update.component';
import { SpecialistaService } from 'app/entities/specialista/specialista.service';
import { Specialista } from 'app/shared/model/specialista.model';

describe('Component Tests', () => {
  describe('Specialista Management Update Component', () => {
    let comp: SpecialistaUpdateComponent;
    let fixture: ComponentFixture<SpecialistaUpdateComponent>;
    let service: SpecialistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [SpecialistaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpecialistaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpecialistaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpecialistaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Specialista(123);
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
        const entity = new Specialista();
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
