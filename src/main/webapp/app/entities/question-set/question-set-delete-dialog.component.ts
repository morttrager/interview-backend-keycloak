import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionSet } from 'app/shared/model/question-set.model';
import { QuestionSetService } from './question-set.service';

@Component({
  templateUrl: './question-set-delete-dialog.component.html',
})
export class QuestionSetDeleteDialogComponent {
  questionSet?: IQuestionSet;

  constructor(
    protected questionSetService: QuestionSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionSetListModification');
      this.activeModal.close();
    });
  }
}
