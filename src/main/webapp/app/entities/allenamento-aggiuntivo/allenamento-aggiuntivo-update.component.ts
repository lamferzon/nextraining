import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAllenamentoAggiuntivo, AllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';
import { AllenamentoAggiuntivoService } from './allenamento-aggiuntivo.service';
import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from 'app/entities/calciatore';

@Component({
  selector: 'jhi-allenamento-aggiuntivo-update',
  templateUrl: './allenamento-aggiuntivo-update.component.html'
})
export class AllenamentoAggiuntivoUpdateComponent implements OnInit {
  allenamentoAggiuntivo: IAllenamentoAggiuntivo;
  isSaving: boolean;

  calciatores: ICalciatore[];

  editForm = this.fb.group({
    id: [],
    sport: [null, [Validators.required, Validators.maxLength(50)]],
    natura: [null, [Validators.required]],
    lavoro: [],
    durata: [],
    calciatores: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected allenamentoAggiuntivoService: AllenamentoAggiuntivoService,
    protected calciatoreService: CalciatoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allenamentoAggiuntivo }) => {
      this.updateForm(allenamentoAggiuntivo);
      this.allenamentoAggiuntivo = allenamentoAggiuntivo;
    });
    this.calciatoreService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICalciatore[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICalciatore[]>) => response.body)
      )
      .subscribe((res: ICalciatore[]) => (this.calciatores = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allenamentoAggiuntivo: IAllenamentoAggiuntivo) {
    this.editForm.patchValue({
      id: allenamentoAggiuntivo.id,
      sport: allenamentoAggiuntivo.sport,
      natura: allenamentoAggiuntivo.natura,
      lavoro: allenamentoAggiuntivo.lavoro,
      durata: allenamentoAggiuntivo.durata,
      calciatores: allenamentoAggiuntivo.calciatores
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allenamentoAggiuntivo = this.createFromForm();
    if (allenamentoAggiuntivo.id !== undefined) {
      this.subscribeToSaveResponse(this.allenamentoAggiuntivoService.update(allenamentoAggiuntivo));
    } else {
      this.subscribeToSaveResponse(this.allenamentoAggiuntivoService.create(allenamentoAggiuntivo));
    }
  }

  private createFromForm(): IAllenamentoAggiuntivo {
    const entity = {
      ...new AllenamentoAggiuntivo(),
      id: this.editForm.get(['id']).value,
      sport: this.editForm.get(['sport']).value,
      natura: this.editForm.get(['natura']).value,
      lavoro: this.editForm.get(['lavoro']).value,
      durata: this.editForm.get(['durata']).value,
      calciatores: this.editForm.get(['calciatores']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllenamentoAggiuntivo>>) {
    result.subscribe((res: HttpResponse<IAllenamentoAggiuntivo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
