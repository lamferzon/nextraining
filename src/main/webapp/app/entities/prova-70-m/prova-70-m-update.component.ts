import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProva70m, Prova70m } from 'app/shared/model/prova-70-m.model';
import { Prova70mService } from './prova-70-m.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-prova-70-m-update',
  templateUrl: './prova-70-m-update.component.html'
})
export class Prova70mUpdateComponent implements OnInit {
  prova70m: IProva70m;
  isSaving: boolean;

  calciatores: ICalciatore[];
  dataProvaDp: any;

  editForm = this.fb.group({
    id: [],
    dataProva: [null, [Validators.required]],
    tempo: [null, [Validators.required]],
    partenzaBlocchi: [],
    velMax: [null, [Validators.max(100)]],
    commento: [],
    condClimatiche: [null, [Validators.maxLength(50)]],
    calciatore: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected prova70mService: Prova70mService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prova70m }) => {
      this.updateForm(prova70m);
      this.prova70m = prova70m;
    });
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(prova70m: IProva70m) {
    this.editForm.patchValue({
      id: prova70m.id,
      dataProva: prova70m.dataProva,
      tempo: prova70m.tempo,
      partenzaBlocchi: prova70m.partenzaBlocchi,
      velMax: prova70m.velMax,
      commento: prova70m.commento,
      condClimatiche: prova70m.condClimatiche,
      calciatore: prova70m.calciatore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prova70m = this.createFromForm();
    if (prova70m.id !== undefined) {
      this.subscribeToSaveResponse(this.prova70mService.update(prova70m));
    } else {
      this.subscribeToSaveResponse(this.prova70mService.create(prova70m));
    }
  }

  private createFromForm(): IProva70m {
    const entity = {
      ...new Prova70m(),
      id: this.editForm.get(['id']).value,
      dataProva: this.editForm.get(['dataProva']).value,
      tempo: this.editForm.get(['tempo']).value,
      partenzaBlocchi: this.editForm.get(['partenzaBlocchi']).value,
      velMax: this.editForm.get(['velMax']).value,
      commento: this.editForm.get(['commento']).value,
      condClimatiche: this.editForm.get(['condClimatiche']).value,
      calciatore: this.editForm.get(['calciatore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProva70m>>) {
    result.subscribe((res: HttpResponse<IProva70m>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
