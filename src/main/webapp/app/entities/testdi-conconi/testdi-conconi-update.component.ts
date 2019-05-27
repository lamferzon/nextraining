import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITestdiConconi, TestdiConconi } from 'app/shared/model/testdi-conconi.model';
import { TestdiConconiService } from './testdi-conconi.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-testdi-conconi-update',
  templateUrl: './testdi-conconi-update.component.html'
})
export class TestdiConconiUpdateComponent implements OnInit {
  testdiConconi: ITestdiConconi;
  isSaving: boolean;

  calciatores: ICalciatore[];

  editForm = this.fb.group({
    id: [],
    fcMax: [null, [Validators.required, Validators.max(220)]],
    sogliaAnaerobica: [null, [Validators.required, Validators.max(220)]],
    velSoglia: [null, [Validators.max(100)]],
    durata: [null, [Validators.required, Validators.max(60)]],
    commento: [],
    condClimatiche: [null, [Validators.maxLength(50)]],
    calciatore: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected testdiConconiService: TestdiConconiService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ testdiConconi }) => {
      this.updateForm(testdiConconi);
      this.testdiConconi = testdiConconi;
    });
    this.calciatoreService
      .query({ filter: 'testdiconconi-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe(
        (res: ICalciatore[]) => {
          if (!this.testdiConconi.calciatore || !this.testdiConconi.calciatore.id) {
            this.calciatores = res;
          } else {
            this.calciatoreService
              .find(this.testdiConconi.calciatore.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICalciatore>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICalciatore>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICalciatore) => (this.calciatores = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(testdiConconi: ITestdiConconi) {
    this.editForm.patchValue({
      id: testdiConconi.id,
      fcMax: testdiConconi.fcMax,
      sogliaAnaerobica: testdiConconi.sogliaAnaerobica,
      velSoglia: testdiConconi.velSoglia,
      durata: testdiConconi.durata,
      commento: testdiConconi.commento,
      condClimatiche: testdiConconi.condClimatiche,
      calciatore: testdiConconi.calciatore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const testdiConconi = this.createFromForm();
    if (testdiConconi.id !== undefined) {
      this.subscribeToSaveResponse(this.testdiConconiService.update(testdiConconi));
    } else {
      this.subscribeToSaveResponse(this.testdiConconiService.create(testdiConconi));
    }
  }

  private createFromForm(): ITestdiConconi {
    const entity = {
      ...new TestdiConconi(),
      id: this.editForm.get(['id']).value,
      fcMax: this.editForm.get(['fcMax']).value,
      sogliaAnaerobica: this.editForm.get(['sogliaAnaerobica']).value,
      velSoglia: this.editForm.get(['velSoglia']).value,
      durata: this.editForm.get(['durata']).value,
      commento: this.editForm.get(['commento']).value,
      condClimatiche: this.editForm.get(['condClimatiche']).value,
      calciatore: this.editForm.get(['calciatore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestdiConconi>>) {
    result.subscribe((res: HttpResponse<ITestdiConconi>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
