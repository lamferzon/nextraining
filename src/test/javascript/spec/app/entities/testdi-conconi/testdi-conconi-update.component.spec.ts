/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { TestdiConconiUpdateComponent } from 'app/entities/testdi-conconi/testdi-conconi-update.component';
import { TestdiConconiService } from 'app/entities/testdi-conconi/testdi-conconi.service';
import { TestdiConconi } from 'app/shared/model/testdi-conconi.model';

describe('Component Tests', () => {
  describe('TestdiConconi Management Update Component', () => {
    let comp: TestdiConconiUpdateComponent;
    let fixture: ComponentFixture<TestdiConconiUpdateComponent>;
    let service: TestdiConconiService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [TestdiConconiUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TestdiConconiUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TestdiConconiUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TestdiConconiService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TestdiConconi(123);
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
        const entity = new TestdiConconi();
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
