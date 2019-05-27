/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { CalciatoreDetailComponent } from 'app/entities/calciatore/calciatore-detail.component';
import { Calciatore } from 'app/shared/model/calciatore.model';

describe('Component Tests', () => {
  describe('Calciatore Management Detail Component', () => {
    let comp: CalciatoreDetailComponent;
    let fixture: ComponentFixture<CalciatoreDetailComponent>;
    const route = ({ data: of({ calciatore: new Calciatore(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [CalciatoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CalciatoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalciatoreDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calciatore).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
