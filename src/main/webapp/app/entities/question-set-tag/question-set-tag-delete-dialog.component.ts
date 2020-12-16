import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionSetTag } from 'app/shared/model/question-set-tag.model';
import { QuestionSetTagService } from './question-set-tag.service';

@Component({
  templateUrl: './question-set-tag-delete-dialog.component.html',
})
export class QuestionSetTagDeleteDialogComponent {
  questionSetTag?: IQuestionSetTag;

  constructor(
    protected questionSetTagService: QuestionSetTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionSetTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionSetTagListModification');
      this.activeModal.close();
    });
  }
}
