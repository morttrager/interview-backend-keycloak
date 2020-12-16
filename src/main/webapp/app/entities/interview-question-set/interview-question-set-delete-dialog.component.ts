import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInterviewQuestionSet } from 'app/shared/model/interview-question-set.model';
import { InterviewQuestionSetService } from './interview-question-set.service';

@Component({
  templateUrl: './interview-question-set-delete-dialog.component.html',
})
export class InterviewQuestionSetDeleteDialogComponent {
  interviewQuestionSet?: IInterviewQuestionSet;

  constructor(
    protected interviewQuestionSetService: InterviewQuestionSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interviewQuestionSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('interviewQuestionSetListModification');
      this.activeModal.close();
    });
  }
}
