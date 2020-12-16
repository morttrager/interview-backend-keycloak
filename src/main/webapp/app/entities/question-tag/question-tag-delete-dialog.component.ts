import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionTag } from 'app/shared/model/question-tag.model';
import { QuestionTagService } from './question-tag.service';

@Component({
  templateUrl: './question-tag-delete-dialog.component.html',
})
export class QuestionTagDeleteDialogComponent {
  questionTag?: IQuestionTag;

  constructor(
    protected questionTagService: QuestionTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionTagService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionTagListModification');
      this.activeModal.close();
    });
  }
}
