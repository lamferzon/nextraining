/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { TestdiCooperUpdateComponent } from 'app/entities/testdi-cooper/testdi-cooper-update.component';
import { TestdiCooperService } from 'app/entities/testdi-cooper/testdi-cooper.service';
import { TestdiCooper } from 'app/shared/model/testdi-cooper.model';

describe('Component Tests', () => {
  describe('TestdiCooper Management Update Component', () => {
    let comp: TestdiCooperUpdateComponent;
    let fixture: ComponentFixture<TestdiCooperUpdateComponent>;
    let service: TestdiCooperService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [TestdiCooperUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TestdiCooperUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestdiCooperUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestdiCooperService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestdiCooper(123);
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
        const entity = new TestdiCooper();
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
