import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICalciatore } from 'app/shared/model/calciatore.model';

@Component({
  selector: 'jhi-calciatore-detail',
  templateUrl: './calciatore-detail.component.html'
})
export class CalciatoreDetailComponent implements OnInit {
  calciatore: ICalciatore;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ calciatore }) => {
      this.calciatore = calciatore;
    });
  }

  previousState() {
    window.history.back();
  }
}
