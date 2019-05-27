import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProva1500m, Prova1500m } from 'app/shared/model/prova-1500-m.model';
import { Prova1500mService } from './prova-1500-m.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-prova-1500-m-update',
  templateUrl: './prova-1500-m-update.component.html'
})
export class Prova1500mUpdateComponent implements OnInit {
  prova1500m: IProva1500m;
  isSaving: boolean;

  calciatores: ICalciatore[];
  dataProvaDp: any;

  editForm = this.fb.group({
    id: [],
    dataProva: [null, [Validators.required]],
    tempo: [null, [Validators.required]],
    tempoKm: [null, [Validators.max(10)]],
    commento: [],
    condClimatiche: [null, [Validators.maxLength(50)]],
    calciatore: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected prova1500mService: Prova1500mService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prova1500m }) => {
      this.updateForm(prova1500m);
      this.prova1500m = prova1500m;
    });
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(prova1500m: IProva1500m) {
    this.editForm.patchValue({
      id: prova1500m.id,
      dataProva: prova1500m.dataProva,
      tempo: prova1500m.tempo,
      tempoKm: prova1500m.tempoKm,
      commento: prova1500m.commento,
      condClimatiche: prova1500m.condClimatiche,
      calciatore: prova1500m.calciatore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prova1500m = this.createFromForm();
    if (prova1500m.id !== undefined) {
      this.subscribeToSaveResponse(this.prova1500mService.update(prova1500m));
    } else {
      this.subscribeToSaveResponse(this.prova1500mService.create(prova1500m));
    }
  }

  private createFromForm(): IProva1500m {
    const entity = {
      ...new Prova1500m(),
      id: this.editForm.get(['id']).value,
      dataProva: this.editForm.get(['dataProva']).value,
      tempo: this.editForm.get(['tempo']).value,
      tempoKm: this.editForm.get(['tempoKm']).value,
      commento: this.editForm.get(['commento']).value,
      condClimatiche: this.editForm.get(['condClimatiche']).value,
      calciatore: this.editForm.get(['calciatore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProva1500m>>) {
    result.subscribe((res: HttpResponse<IProva1500m>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
