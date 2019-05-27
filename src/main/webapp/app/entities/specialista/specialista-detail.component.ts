import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialista } from 'app/shared/model/specialista.model';

@Component({
  selector: 'jhi-specialista-detail',
  templateUrl: './specialista-detail.component.html'
})
export class SpecialistaDetailComponent implements OnInit {
  specialista: ISpecialista;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ specialista }) => {
      this.specialista = specialista;
    });
  }

  previousState() {
    window.history.back();
  }
}
