import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProva1500m } from 'app/shared/model/prova-1500-m.model';
import { Prova1500mService } from './prova-1500-m.service';

@Component({
  selector: 'jhi-prova-1500-m-delete-dialog',
  templateUrl: './prova-1500-m-delete-dialog.component.html'
})
export class Prova1500mDeleteDialogComponent {
  prova1500m: IProva1500m;

  constructor(
    protected prova1500mService: Prova1500mService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.prova1500mService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'prova1500mListModification',
        content: 'Deleted an prova1500m'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-prova-1500-m-delete-popup',
  template: ''
})
export class Prova1500mDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prova1500m }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Prova1500mDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.prova1500m = prova1500m;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prova-1500-m', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prova-1500-m', { outlets: { popup: null } }]);
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
