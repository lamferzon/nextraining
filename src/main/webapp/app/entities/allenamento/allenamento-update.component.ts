import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAllenamento, Allenamento } from 'app/shared/model/allenamento.model';
import { AllenamentoService } from './allenamento.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-allenamento-update',
  templateUrl: './allenamento-update.component.html'
})
export class AllenamentoUpdateComponent implements OnInit {
  allenamento: IAllenamento;
  isSaving: boolean;

  calciatores: ICalciatore[];
  dataSvolgimentoDp: any;

  editForm = this.fb.group({
    id: [],
    dataSvolgimento: [null, [Validators.required]],
    natura: [null, [Validators.required]],
    lavoro: [],
    calciatores: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected allenamentoService: AllenamentoService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allenamento }) => {
      this.updateForm(allenamento);
      this.allenamento = allenamento;
    });
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allenamento: IAllenamento) {
    this.editForm.patchValue({
      id: allenamento.id,
      dataSvolgimento: allenamento.dataSvolgimento,
      natura: allenamento.natura,
      lavoro: allenamento.lavoro,
      calciatores: allenamento.calciatores
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allenamento = this.createFromForm();
    if (allenamento.id !== undefined) {
      this.subscribeToSaveResponse(this.allenamentoService.update(allenamento));
    } else {
      this.subscribeToSaveResponse(this.allenamentoService.create(allenamento));
    }
  }

  private createFromForm(): IAllenamento {
    const entity = {
      ...new Allenamento(),
      id: this.editForm.get(['id']).value,
      dataSvolgimento: this.editForm.get(['dataSvolgimento']).value,
      natura: this.editForm.get(['natura']).value,
      lavoro: this.editForm.get(['lavoro']).value,
      calciatores: this.editForm.get(['calciatores']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllenamento>>) {
    result.subscribe((res: HttpResponse<IAllenamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
