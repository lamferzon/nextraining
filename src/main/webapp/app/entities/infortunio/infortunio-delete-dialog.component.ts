import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInfortunio } from 'app/shared/model/infortunio.model';
import { InfortunioService } from './infortunio.service';

@Component({
  selector: 'jhi-infortunio-delete-dialog',
  templateUrl: './infortunio-delete-dialog.component.html'
})
export class InfortunioDeleteDialogComponent {
  infortunio: IInfortunio;

  constructor(
    protected infortunioService: InfortunioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.infortunioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'infortunioListModification',
        content: 'Deleted an infortunio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-infortunio-delete-popup',
  template: ''
})
export class InfortunioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ infortunio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InfortunioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.infortunio = infortunio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/infortunio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/infortunio', { outlets: { popup: null } }]);
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
