/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { Prova1500mUpdateComponent } from 'app/entities/prova-1500-m/prova-1500-m-update.component';
import { Prova1500mService } from 'app/entities/prova-1500-m/prova-1500-m.service';
import { Prova1500m } from 'app/shared/model/prova-1500-m.model';

describe('Component Tests', () => {
  describe('Prova1500m Management Update Component', () => {
    let comp: Prova1500mUpdateComponent;
    let fixture: ComponentFixture<Prova1500mUpdateComponent>;
    let service: Prova1500mService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [Prova1500mUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Prova1500mUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Prova1500mUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Prova1500mService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Prova1500m(123);
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
        const entity = new Prova1500m();
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
