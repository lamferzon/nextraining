import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestdiCooper } from 'app/shared/model/testdi-cooper.model';
import { TestdiCooperService } from './testdi-cooper.service';

@Component({
  selector: 'jhi-testdi-cooper-delete-dialog',
  templateUrl: './testdi-cooper-delete-dialog.component.html'
})
export class TestdiCooperDeleteDialogComponent {
  testdiCooper: ITestdiCooper;

  constructor(
    protected testdiCooperService: TestdiCooperService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.testdiCooperService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'testdiCooperListModification',
        content: 'Deleted an testdiCooper'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-testdi-cooper-delete-popup',
  template: ''
})
export class TestdiCooperDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ testdiCooper }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TestdiCooperDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.testdiCooper = testdiCooper;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/testdi-cooper', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/testdi-cooper', { outlets: { popup: null } }]);
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
