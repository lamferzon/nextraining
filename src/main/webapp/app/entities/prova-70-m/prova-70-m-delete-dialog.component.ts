import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProva70m } from 'app/shared/model/prova-70-m.model';
import { Prova70mService } from './prova-70-m.service';

@Component({
  selector: 'jhi-prova-70-m-delete-dialog',
  templateUrl: './prova-70-m-delete-dialog.component.html'
})
export class Prova70mDeleteDialogComponent {
  prova70m: IProva70m;

  constructor(protected prova70mService: Prova70mService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.prova70mService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'prova70mListModification',
        content: 'Deleted an prova70m'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-prova-70-m-delete-popup',
  template: ''
})
export class Prova70mDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prova70m }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Prova70mDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.prova70m = prova70m;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/prova-70-m', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/prova-70-m', { outlets: { popup: null } }]);
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
