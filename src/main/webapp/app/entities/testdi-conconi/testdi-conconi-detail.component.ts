import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITestdiConconi } from 'app/shared/model/testdi-conconi.model';

@Component({
  selector: 'jhi-testdi-conconi-detail',
  templateUrl: './testdi-conconi-detail.component.html'
})
export class TestdiConconiDetailComponent implements OnInit {
  testdiConconi: ITestdiConconi;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ testdiConconi }) => {
      this.testdiConconi = testdiConconi;
    });
  }

  previousState() {
    window.history.back();
  }
}
