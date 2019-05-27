import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITestdiConconi } from 'app/shared/model/testdi-conconi.model';
import { TestdiConconiService } from './testdi-conconi.service';

@Component({
  selector: 'jhi-testdi-conconi-delete-dialog',
  templateUrl: './testdi-conconi-delete-dialog.component.html'
})
export class TestdiConconiDeleteDialogComponent {
  testdiConconi: ITestdiConconi;

  constructor(
    protected testdiConconiService: TestdiConconiService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.testdiConconiService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'testdiConconiListModification',
        content: 'Deleted an testdiConconi'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-testdi-conconi-delete-popup',
  template: ''
})
export class TestdiConconiDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ testdiConconi }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TestdiConconiDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.testdiConconi = testdiConconi;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/testdi-conconi', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/testdi-conconi', { outlets: { popup: null } }]);
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
