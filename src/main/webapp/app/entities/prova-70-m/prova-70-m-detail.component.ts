import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProva70m } from 'app/shared/model/prova-70-m.model';

@Component({
  selector: 'jhi-prova-70-m-detail',
  templateUrl: './prova-70-m-detail.component.html'
})
export class Prova70mDetailComponent implements OnInit {
  prova70m: IProva70m;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prova70m }) => {
      this.prova70m = prova70m;
    });
  }

  previousState() {
    window.history.back();
  }
}
