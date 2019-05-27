/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { ParametriFisiciUpdateComponent } from 'app/entities/parametri-fisici/parametri-fisici-update.component';
import { ParametriFisiciService } from 'app/entities/parametri-fisici/parametri-fisici.service';
import { ParametriFisici } from 'app/shared/model/parametri-fisici.model';

describe('Component Tests', () => {
  describe('ParametriFisici Management Update Component', () => {
    let comp: ParametriFisiciUpdateComponent;
    let fixture: ComponentFixture<ParametriFisiciUpdateComponent>;
    let service: ParametriFisiciService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [ParametriFisiciUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParametriFisiciUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParametriFisiciUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParametriFisiciService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParametriFisici(123);
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
        const entity = new ParametriFisici();
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
