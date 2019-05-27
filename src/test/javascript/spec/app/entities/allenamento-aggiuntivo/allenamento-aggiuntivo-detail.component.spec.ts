/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { AllenamentoAggiuntivoDetailComponent } from 'app/entities/allenamento-aggiuntivo/allenamento-aggiuntivo-detail.component';
import { AllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';

describe('Component Tests', () => {
  describe('AllenamentoAggiuntivo Management Detail Component', () => {
    let comp: AllenamentoAggiuntivoDetailComponent;
    let fixture: ComponentFixture<AllenamentoAggiuntivoDetailComponent>;
    const route = ({ data: of({ allenamentoAggiuntivo: new AllenamentoAggiuntivo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [AllenamentoAggiuntivoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AllenamentoAggiuntivoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllenamentoAggiuntivoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.allenamentoAggiuntivo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
