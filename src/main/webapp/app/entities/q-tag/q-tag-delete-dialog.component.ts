import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQTag } from 'app/shared/model/q-tag.model';
import { QTagService } from './q-tag.service';

@Component({
  templateUrl: './q-tag-delete-dialog.component.html',
})
export class QTagDeleteDialogComponent {
  qTag?: IQTag;

  constructor(protected qTagService: QTagService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('qTagListModification');
      this.activeModal.close();
    });
  }
}
