import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProva1500m } from 'app/shared/model/prova-1500-m.model';

@Component({
  selector: 'jhi-prova-1500-m-detail',
  templateUrl: './prova-1500-m-detail.component.html'
})
export class Prova1500mDetailComponent implements OnInit {
  prova1500m: IProva1500m;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prova1500m }) => {
      this.prova1500m = prova1500m;
    });
  }

  previousState() {
    window.history.back();
  }
}
