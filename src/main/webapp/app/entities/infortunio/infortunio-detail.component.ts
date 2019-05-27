import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfortunio } from 'app/shared/model/infortunio.model';

@Component({
  selector: 'jhi-infortunio-detail',
  templateUrl: './infortunio-detail.component.html'
})
export class InfortunioDetailComponent implements OnInit {
  infortunio: IInfortunio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ infortunio }) => {
      this.infortunio = infortunio;
    });
  }

  previousState() {
    window.history.back();
  }
}
