/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { Prova1500mDetailComponent } from 'app/entities/prova-1500-m/prova-1500-m-detail.component';
import { Prova1500m } from 'app/shared/model/prova-1500-m.model';

describe('Component Tests', () => {
  describe('Prova1500m Management Detail Component', () => {
    let comp: Prova1500mDetailComponent;
    let fixture: ComponentFixture<Prova1500mDetailComponent>;
    const route = ({ data: of({ prova1500m: new Prova1500m(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [Prova1500mDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Prova1500mDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Prova1500mDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prova1500m).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
