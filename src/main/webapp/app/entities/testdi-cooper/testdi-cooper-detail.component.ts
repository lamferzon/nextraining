import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITestdiCooper } from 'app/shared/model/testdi-cooper.model';

@Component({
  selector: 'jhi-testdi-cooper-detail',
  templateUrl: './testdi-cooper-detail.component.html'
})
export class TestdiCooperDetailComponent implements OnInit {
  testdiCooper: ITestdiCooper;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ testdiCooper }) => {
      this.testdiCooper = testdiCooper;
    });
  }

  previousState() {
    window.history.back();
  }
}
