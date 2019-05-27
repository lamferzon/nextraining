import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';

@Component({
  selector: 'jhi-allenamento-aggiuntivo-detail',
  templateUrl: './allenamento-aggiuntivo-detail.component.html'
})
export class AllenamentoAggiuntivoDetailComponent implements OnInit {
  allenamentoAggiuntivo: IAllenamentoAggiuntivo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allenamentoAggiuntivo }) => {
      this.allenamentoAggiuntivo = allenamentoAggiuntivo;
    });
  }

  previousState() {
    window.history.back();
  }
}
