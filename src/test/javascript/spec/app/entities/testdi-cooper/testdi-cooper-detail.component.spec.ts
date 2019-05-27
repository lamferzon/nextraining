/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { TestdiCooperDetailComponent } from 'app/entities/testdi-cooper/testdi-cooper-detail.component';
import { TestdiCooper } from 'app/shared/model/testdi-cooper.model';

describe('Component Tests', () => {
  describe('TestdiCooper Management Detail Component', () => {
    let comp: TestdiCooperDetailComponent;
    let fixture: ComponentFixture<TestdiCooperDetailComponent>;
    const route = ({ data: of({ testdiCooper: new TestdiCooper(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [TestdiCooperDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TestdiCooperDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestdiCooperDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testdiCooper).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
