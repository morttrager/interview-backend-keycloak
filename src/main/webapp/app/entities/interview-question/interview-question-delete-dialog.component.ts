import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInterviewQuestion } from 'app/shared/model/interview-question.model';
import { InterviewQuestionService } from './interview-question.service';

@Component({
  templateUrl: './interview-question-delete-dialog.component.html',
})
export class InterviewQuestionDeleteDialogComponent {
  interviewQuestion?: IInterviewQuestion;

  constructor(
    protected interviewQuestionService: InterviewQuestionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.interviewQuestionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('interviewQuestionListModification');
      this.activeModal.close();
    });
  }
}
