import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITestdiCooper, TestdiCooper } from 'app/shared/model/testdi-cooper.model';
import { TestdiCooperService } from './testdi-cooper.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-testdi-cooper-update',
  templateUrl: './testdi-cooper-update.component.html'
})
export class TestdiCooperUpdateComponent implements OnInit {
  testdiCooper: ITestdiCooper;
  isSaving: boolean;

  calciatores: ICalciatore[];
  dataTestDp: any;

  editForm = this.fb.group({
    id: [],
    dataTest: [null, [Validators.required]],
    distanza: [null, [Validators.required, Validators.max(10)]],
    v02Max: [null, [Validators.max(120)]],
    commento: [],
    condClimatiche: [null, [Validators.maxLength(50)]],
    calciatore: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected testdiCooperService: TestdiCooperService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ testdiCooper }) => {
      this.updateForm(testdiCooper);
      this.testdiCooper = testdiCooper;
    });
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(testdiCooper: ITestdiCooper) {
    this.editForm.patchValue({
      id: testdiCooper.id,
      dataTest: testdiCooper.dataTest,
      distanza: testdiCooper.distanza,
      v02Max: testdiCooper.v02Max,
      commento: testdiCooper.commento,
      condClimatiche: testdiCooper.condClimatiche,
      calciatore: testdiCooper.calciatore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const testdiCooper = this.createFromForm();
    if (testdiCooper.id !== undefined) {
      this.subscribeToSaveResponse(this.testdiCooperService.update(testdiCooper));
    } else {
      this.subscribeToSaveResponse(this.testdiCooperService.create(testdiCooper));
    }
  }

  private createFromForm(): ITestdiCooper {
    const entity = {
      ...new TestdiCooper(),
      id: this.editForm.get(['id']).value,
      dataTest: this.editForm.get(['dataTest']).value,
      distanza: this.editForm.get(['distanza']).value,
      v02Max: this.editForm.get(['v02Max']).value,
      commento: this.editForm.get(['commento']).value,
      condClimatiche: this.editForm.get(['condClimatiche']).value,
      calciatore: this.editForm.get(['calciatore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestdiCooper>>) {
    result.subscribe((res: HttpResponse<ITestdiCooper>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCalciatoreById(index: number, item: ICalciatore) {
    return item.id;
  }
}
