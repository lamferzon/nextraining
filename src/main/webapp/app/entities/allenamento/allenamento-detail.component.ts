import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllenamento } from 'app/shared/model/allenamento.model';

@Component({
  selector: 'jhi-allenamento-detail',
  templateUrl: './allenamento-detail.component.html'
})
export class AllenamentoDetailComponent implements OnInit {
  allenamento: IAllenamento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allenamento }) => {
      this.allenamento = allenamento;
    });
  }

  previousState() {
    window.history.back();
  }
}
