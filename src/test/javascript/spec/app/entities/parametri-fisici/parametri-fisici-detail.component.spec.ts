/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { ParametriFisiciDetailComponent } from 'app/entities/parametri-fisici/parametri-fisici-detail.component';
import { ParametriFisici } from 'app/shared/model/parametri-fisici.model';

describe('Component Tests', () => {
  describe('ParametriFisici Management Detail Component', () => {
    let comp: ParametriFisiciDetailComponent;
    let fixture: ComponentFixture<ParametriFisiciDetailComponent>;
    const route = ({ data: of({ parametriFisici: new ParametriFisici(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [ParametriFisiciDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParametriFisiciDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParametriFisiciDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parametriFisici).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
