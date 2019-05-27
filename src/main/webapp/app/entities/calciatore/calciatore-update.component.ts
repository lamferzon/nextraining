import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICalciatore, Calciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from './calciatore.service';
import { IAllenamento } from 'app/shared/model/allenamento.model';
import { AllenamentoService } from 'app/entities/allenamento';
import { IAllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';
import { AllenamentoAggiuntivoService } from 'app/entities/allenamento-aggiuntivo';

@Component({
  selector: 'jhi-calciatore-update',
  templateUrl: './calciatore-update.component.html'
})
export class CalciatoreUpdateComponent implements OnInit {
  calciatore: ICalciatore;
  isSaving: boolean;

  allenamentos: IAllenamento[];

  allenamentoaggiuntivos: IAllenamentoAggiuntivo[];
  dataNascitaDp: any;

  editForm = this.fb.group({
    id: [],
    codFiscale: [null, [Validators.maxLength(16)]],
    cognome: [null, [Validators.required, Validators.maxLength(50)]],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    dataNascita: [],
    numTelefono: [null, [Validators.required, Validators.maxLength(20)]],
    email: [null, [Validators.maxLength(50)]],
    reparto: [],
    ruolo: [],
    selettore: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected calciatoreService: CalciatoreService,
    protected allenamentoService: AllenamentoService,
    protected allenamentoAggiuntivoService: AllenamentoAggiuntivoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ calciatore }) => {
      this.updateForm(calciatore);
      this.calciatore = calciatore;
    });
    this.allenamentoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAllenamento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAllenamento[]>) => response.body)
      )
      .subscribe((res: IAllenamento[]) => (this.allenamentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.allenamentoAggiuntivoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAllenamentoAggiuntivo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAllenamentoAggiuntivo[]>) => response.body)
      )
      .subscribe(
        (res: IAllenamentoAggiuntivo[]) => (this.allenamentoaggiuntivos = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(calciatore: ICalciatore) {
    this.editForm.patchValue({
      id: calciatore.id,
      codFiscale: calciatore.codFiscale,
      cognome: calciatore.cognome,
      nome: calciatore.nome,
      dataNascita: calciatore.dataNascita,
      numTelefono: calciatore.numTelefono,
      email: calciatore.email,
      reparto: calciatore.reparto,
      ruolo: calciatore.ruolo,
      selettore: calciatore.selettore
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const calciatore = this.createFromForm();
    if (calciatore.id !== undefined) {
      this.subscribeToSaveResponse(this.calciatoreService.update(calciatore));
    } else {
      this.subscribeToSaveResponse(this.calciatoreService.create(calciatore));
    }
  }

  private createFromForm(): ICalciatore {
    const entity = {
      ...new Calciatore(),
      id: this.editForm.get(['id']).value,
      codFiscale: this.editForm.get(['codFiscale']).value,
      cognome: this.editForm.get(['cognome']).value,
      nome: this.editForm.get(['nome']).value,
      dataNascita: this.editForm.get(['dataNascita']).value,
      numTelefono: this.editForm.get(['numTelefono']).value,
      email: this.editForm.get(['email']).value,
      reparto: this.editForm.get(['reparto']).value,
      ruolo: this.editForm.get(['ruolo']).value,
      selettore: this.editForm.get(['selettore']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalciatore>>) {
    result.subscribe((res: HttpResponse<ICalciatore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAllenamentoById(index: number, item: IAllenamento) {
    return item.id;
  }

  trackAllenamentoAggiuntivoById(index: number, item: IAllenamentoAggiuntivo) {
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
