import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParametriFisici } from 'app/shared/model/parametri-fisici.model';
import { ParametriFisiciService } from './parametri-fisici.service';

@Component({
  selector: 'jhi-parametri-fisici-delete-dialog',
  templateUrl: './parametri-fisici-delete-dialog.component.html'
})
export class ParametriFisiciDeleteDialogComponent {
  parametriFisici: IParametriFisici;

  constructor(
    protected parametriFisiciService: ParametriFisiciService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.parametriFisiciService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'parametriFisiciListModification',
        content: 'Deleted an parametriFisici'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-parametri-fisici-delete-popup',
  template: ''
})
export class ParametriFisiciDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ parametriFisici }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ParametriFisiciDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.parametriFisici = parametriFisici;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/parametri-fisici', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/parametri-fisici', { outlets: { popup: null } }]);
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
