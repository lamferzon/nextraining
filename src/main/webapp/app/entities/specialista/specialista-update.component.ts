import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISpecialista, Specialista } from 'app/shared/model/specialista.model';
import { SpecialistaService } from './specialista.service';

@Component({
  selector: 'jhi-specialista-update',
  templateUrl: './specialista-update.component.html'
})
export class SpecialistaUpdateComponent implements OnInit {
  specialista: ISpecialista;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    codFiscale: [null, [Validators.maxLength(16)]],
    cognome: [null, [Validators.required, Validators.maxLength(50)]],
    nome: [null, [Validators.required, Validators.maxLength(50)]],
    specializzazione: [null, [Validators.maxLength(50)]],
    numTelefono: [null, [Validators.required, Validators.maxLength(20)]],
    email: [null, [Validators.maxLength(40)]],
    indirizzoStudio: [null, [Validators.maxLength(50)]],
    paeseStudio: [null, [Validators.maxLength(50)]]
  });

  constructor(protected specialistaService: SpecialistaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ specialista }) => {
      this.updateForm(specialista);
      this.specialista = specialista;
    });
  }

  updateForm(specialista: ISpecialista) {
    this.editForm.patchValue({
      id: specialista.id,
      codFiscale: specialista.codFiscale,
      cognome: specialista.cognome,
      nome: specialista.nome,
      specializzazione: specialista.specializzazione,
      numTelefono: specialista.numTelefono,
      email: specialista.email,
      indirizzoStudio: specialista.indirizzoStudio,
      paeseStudio: specialista.paeseStudio
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const specialista = this.createFromForm();
    if (specialista.id !== undefined) {
      this.subscribeToSaveResponse(this.specialistaService.update(specialista));
    } else {
      this.subscribeToSaveResponse(this.specialistaService.create(specialista));
    }
  }

  private createFromForm(): ISpecialista {
    const entity = {
      ...new Specialista(),
      id: this.editForm.get(['id']).value,
      codFiscale: this.editForm.get(['codFiscale']).value,
      cognome: this.editForm.get(['cognome']).value,
      nome: this.editForm.get(['nome']).value,
      specializzazione: this.editForm.get(['specializzazione']).value,
      numTelefono: this.editForm.get(['numTelefono']).value,
      email: this.editForm.get(['email']).value,
      indirizzoStudio: this.editForm.get(['indirizzoStudio']).value,
      paeseStudio: this.editForm.get(['paeseStudio']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialista>>) {
    result.subscribe((res: HttpResponse<ISpecialista>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
