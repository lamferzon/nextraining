import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalciatore } from 'app/shared/model/calciatore.model';
import { CalciatoreService } from './calciatore.service';

@Component({
  selector: 'jhi-calciatore-delete-dialog',
  templateUrl: './calciatore-delete-dialog.component.html'
})
export class CalciatoreDeleteDialogComponent {
  calciatore: ICalciatore;

  constructor(
    protected calciatoreService: CalciatoreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.calciatoreService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'calciatoreListModification',
        content: 'Deleted an calciatore'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-calciatore-delete-popup',
  template: ''
})
export class CalciatoreDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ calciatore }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CalciatoreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.calciatore = calciatore;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/calciatore', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/calciatore', { outlets: { popup: null } }]);
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
