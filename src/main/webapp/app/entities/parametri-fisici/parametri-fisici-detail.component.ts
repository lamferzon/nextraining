import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParametriFisici } from 'app/shared/model/parametri-fisici.model';

@Component({
  selector: 'jhi-parametri-fisici-detail',
  templateUrl: './parametri-fisici-detail.component.html'
})
export class ParametriFisiciDetailComponent implements OnInit {
  parametriFisici: IParametriFisici;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parametriFisici }) => {
      this.parametriFisici = parametriFisici;
    });
  }

  previousState() {
    window.history.back();
  }
}
