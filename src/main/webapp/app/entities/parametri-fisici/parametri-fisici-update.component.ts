import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IParametriFisici, ParametriFisici } from 'app/shared/model/parametri-fisici.model';
import { ParametriFisiciService } from './parametri-fisici.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-parametri-fisici-update',
  templateUrl: './parametri-fisici-update.component.html'
})
export class ParametriFisiciUpdateComponent implements OnInit {
  parametriFisici: IParametriFisici;
  isSaving: boolean;

  calciatores: ICalciatore[];
  dataRivelazioneDp: any;

  editForm = this.fb.group({
    id: [],
    dataRivelazione: [null, [Validators.required]],
    massaCorporea: [null, [Validators.required, Validators.max(150)]],
    altezza: [null, [Validators.required, Validators.max(250)]],
    bmi: [null, [Validators.max(50)]],
    condizione: [],
    fcRiposo: [null, [Validators.max(150)]],
    calciatore: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected parametriFisiciService: ParametriFisiciService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ parametriFisici }) => {
      this.updateForm(parametriFisici);
      this.parametriFisici = parametriFisici;
    });
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(parametriFisici: IParametriFisici) {
    this.editForm.patchValue({
      id: parametriFisici.id,
      dataRivelazione: parametriFisici.dataRivelazione,
      massaCorporea: parametriFisici.massaCorporea,
      altezza: parametriFisici.altezza,
      bmi: parametriFisici.bmi,
      condizione: parametriFisici.condizione,
      fcRiposo: parametriFisici.fcRiposo,
      calciatore: parametriFisici.calciatore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const parametriFisici = this.createFromForm();
    if (parametriFisici.id !== undefined) {
      this.subscribeToSaveResponse(this.parametriFisiciService.update(parametriFisici));
    } else {
      this.subscribeToSaveResponse(this.parametriFisiciService.create(parametriFisici));
    }
  }

  private createFromForm(): IParametriFisici {
    const entity = {
      ...new ParametriFisici(),
      id: this.editForm.get(['id']).value,
      dataRivelazione: this.editForm.get(['dataRivelazione']).value,
      massaCorporea: this.editForm.get(['massaCorporea']).value,
      altezza: this.editForm.get(['altezza']).value,
      bmi: this.editForm.get(['bmi']).value,
      condizione: this.editForm.get(['condizione']).value,
      fcRiposo: this.editForm.get(['fcRiposo']).value,
      calciatore: this.editForm.get(['calciatore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParametriFisici>>) {
    result.subscribe((res: HttpResponse<IParametriFisici>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
