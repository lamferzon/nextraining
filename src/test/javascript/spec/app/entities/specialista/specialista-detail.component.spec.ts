/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NextrainingTestModule } from '../../../test.module';
import { SpecialistaDetailComponent } from 'app/entities/specialista/specialista-detail.component';
import { Specialista } from 'app/shared/model/specialista.model';

describe('Component Tests', () => {
  describe('Specialista Management Detail Component', () => {
    let comp: SpecialistaDetailComponent;
    let fixture: ComponentFixture<SpecialistaDetailComponent>;
    const route = ({ data: of({ specialista: new Specialista(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NextrainingTestModule],
        declarations: [SpecialistaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpecialistaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialistaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.specialista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
