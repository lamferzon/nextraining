/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { AllenamentoDetailComponent } from 'app/entities/allenamento/allenamento-detail.component';
import { Allenamento } from 'app/shared/model/allenamento.model';

describe('Component Tests', () => {
  describe('Allenamento Management Detail Component', () => {
    let comp: AllenamentoDetailComponent;
    let fixture: ComponentFixture<AllenamentoDetailComponent>;
    const route = ({ data: of({ allenamento: new Allenamento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [AllenamentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllenamentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllenamentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allenamento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
