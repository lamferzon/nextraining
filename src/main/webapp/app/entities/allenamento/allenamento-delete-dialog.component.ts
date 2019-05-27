import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllenamento } from 'app/shared/model/allenamento.model';
import { AllenamentoService } from './allenamento.service';

@Component({
  selector: 'jhi-allenamento-delete-dialog',
  templateUrl: './allenamento-delete-dialog.component.html'
})
export class AllenamentoDeleteDialogComponent {
  allenamento: IAllenamento;

  constructor(
    protected allenamentoService: AllenamentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.allenamentoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'allenamentoListModification',
        content: 'Deleted an allenamento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-allenamento-delete-popup',
  template: ''
})
export class AllenamentoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ allenamento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AllenamentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.allenamento = allenamento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/allenamento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/allenamento', { outlets: { popup: null } }]);
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
