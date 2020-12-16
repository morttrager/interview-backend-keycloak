import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInterviewee } from 'app/shared/model/interviewee.model';
import { IntervieweeService } from './interviewee.service';

@Component({
  templateUrl: './interviewee-delete-dialog.component.html',
})
export class IntervieweeDeleteDialogComponent {
  interviewee?: IInterviewee;

  constructor(
    protected intervieweeService: IntervieweeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.intervieweeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('intervieweeListModification');
      this.activeModal.close();
    });
  }
}
