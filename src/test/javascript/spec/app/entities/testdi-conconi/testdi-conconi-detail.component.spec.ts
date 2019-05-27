/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { TestdiConconiDetailComponent } from 'app/entities/testdi-conconi/testdi-conconi-detail.component';
import { TestdiConconi } from 'app/shared/model/testdi-conconi.model';

describe('Component Tests', () => {
  describe('TestdiConconi Management Detail Component', () => {
    let comp: TestdiConconiDetailComponent;
    let fixture: ComponentFixture<TestdiConconiDetailComponent>;
    const route = ({ data: of({ testdiConconi: new TestdiConconi(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [TestdiConconiDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TestdiConconiDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestdiConconiDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testdiConconi).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
