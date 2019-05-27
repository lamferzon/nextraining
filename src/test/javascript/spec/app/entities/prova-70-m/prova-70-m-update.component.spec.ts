/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { Prova70mUpdateComponent } from 'app/entities/prova-70-m/prova-70-m-update.component';
import { Prova70mService } from 'app/entities/prova-70-m/prova-70-m.service';
import { Prova70m } from 'app/shared/model/prova-70-m.model';

describe('Component Tests', () => {
  describe('Prova70m Management Update Component', () => {
    let comp: Prova70mUpdateComponent;
    let fixture: ComponentFixture<Prova70mUpdateComponent>;
    let service: Prova70mService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [Prova70mUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Prova70mUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Prova70mUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Prova70mService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Prova70m(123);
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
        const entity = new Prova70m();
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
