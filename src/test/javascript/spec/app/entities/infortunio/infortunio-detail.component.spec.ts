/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { InfortunioDetailComponent } from 'app/entities/infortunio/infortunio-detail.component';
import { Infortunio } from 'app/shared/model/infortunio.model';

describe('Component Tests', () => {
  describe('Infortunio Management Detail Component', () => {
    let comp: InfortunioDetailComponent;
    let fixture: ComponentFixture<InfortunioDetailComponent>;
    const route = ({ data: of({ infortunio: new Infortunio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [InfortunioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InfortunioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InfortunioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.infortunio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
