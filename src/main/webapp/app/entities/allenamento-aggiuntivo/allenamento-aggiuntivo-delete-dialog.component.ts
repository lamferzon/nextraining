import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';
import { AllenamentoAggiuntivoService } from './allenamento-aggiuntivo.service';

@Component({
  selector: 'jhi-allenamento-aggiuntivo-delete-dialog',
  templateUrl: './allenamento-aggiuntivo-delete-dialog.component.html'
})
export class AllenamentoAggiuntivoDeleteDialogComponent {
  allenamentoAggiuntivo: IAllenamentoAggiuntivo;

  constructor(
    protected allenamentoAggiuntivoService: AllenamentoAggiuntivoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allenamentoAggiuntivoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allenamentoAggiuntivoListModification',
        content: 'Deleted an allenamentoAggiuntivo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allenamento-aggiuntivo-delete-popup',
  template: ''
})
export class AllenamentoAggiuntivoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allenamentoAggiuntivo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllenamentoAggiuntivoDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.allenamentoAggiuntivo = allenamentoAggiuntivo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allenamento-aggiuntivo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allenamento-aggiuntivo', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
