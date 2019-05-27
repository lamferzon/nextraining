/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { Prova70mDetailComponent } from 'app/entities/prova-70-m/prova-70-m-detail.component';
import { Prova70m } from 'app/shared/model/prova-70-m.model';

describe('Component Tests', () => {
  describe('Prova70m Management Detail Component', () => {
    let comp: Prova70mDetailComponent;
    let fixture: ComponentFixture<Prova70mDetailComponent>;
    const route = ({ data: of({ prova70m: new Prova70m(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [Prova70mDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Prova70mDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Prova70mDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prova70m).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
