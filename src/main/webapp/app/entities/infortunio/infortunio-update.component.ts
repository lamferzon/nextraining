import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInfortunio, Infortunio } from 'app/shared/model/infortunio.model';
import { InfortunioService } from './infortunio.service';
import { ISpecialista } from 'app/shared/model/specialista.model';
import { SpecialistaService } from 'app/entities/specialista';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-infortunio-update',
  templateUrl: './infortunio-update.component.html'
})
export class InfortunioUpdateComponent implements OnInit {
  infortunio: IInfortunio;
  isSaving: boolean;

  specialistas: ISpecialista[];

  calciatores: ICalciatore[];
  dataInizioDp: any;
  dataFineDp: any;

  editForm = this.fb.group({
    id: [],
    dataInizio: [null, [Validators.required]],
    dataFine: [],
    gravita: [null, [Validators.required]],
    descrizione: [null, [Validators.maxLength(50)]],
    specialista: [],
    calciatore: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected infortunioService: InfortunioService,
    protected specialistaService: SpecialistaService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ infortunio }) => {
      this.updateForm(infortunio);
      this.infortunio = infortunio;
    });
    this.specialistaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISpecialista[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISpecialista[]>) => response.body)
      )
      .subscribe((res: ISpecialista[]) => (this.specialistas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(infortunio: IInfortunio) {
    this.editForm.patchValue({
      id: infortunio.id,
      dataInizio: infortunio.dataInizio,
      dataFine: infortunio.dataFine,
      gravita: infortunio.gravita,
      descrizione: infortunio.descrizione,
      specialista: infortunio.specialista,
      calciatore: infortunio.calciatore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const infortunio = this.createFromForm();
    if (infortunio.id !== undefined) {
      this.subscribeToSaveResponse(this.infortunioService.update(infortunio));
    } else {
      this.subscribeToSaveResponse(this.infortunioService.create(infortunio));
    }
  }

  private createFromForm(): IInfortunio {
    const entity = {
      ...new Infortunio(),
      id: this.editForm.get(['id']).value,
      dataInizio: this.editForm.get(['dataInizio']).value,
      dataFine: this.editForm.get(['dataFine']).value,
      gravita: this.editForm.get(['gravita']).value,
      descrizione: this.editForm.get(['descrizione']).value,
      specialista: this.editForm.get(['specialista']).value,
      calciatore: this.editForm.get(['calciatore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfortunio>>) {
    result.subscribe((res: HttpResponse<IInfortunio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackSpecialistaById(index: number, item: ISpecialista) {
    return item.id;
  }

  trackCalciatoreById(index: number, item: ICalciatore) {
    return item.id;
  }
}
