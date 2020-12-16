import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInterviewQuestion } from 'app/shared/model/interview-question.model';
import { InterviewQuestionService } from './interview-question.service';
import { InterviewQuestionDeleteDialogComponent } from './interview-question-delete-dialog.component';

@Component({
  selector: 'jhi-interview-question',
  templateUrl: './interview-question.component.html',
})
export class InterviewQuestionComponent implements OnInit, OnDestroy {
  interviewQuestions?: IInterviewQuestion[];
  eventSubscriber?: Subscription;

  constructor(
    protected interviewQuestionService: InterviewQuestionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.interviewQuestionService
      .query()
      .subscribe((res: HttpResponse<IInterviewQuestion[]>) => (this.interviewQuestions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInterviewQuestions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInterviewQuestion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInterviewQuestions(): void {
    this.eventSubscriber = this.eventManager.subscribe('interviewQuestionListModification', () => this.loadAll());
  }

  delete(interviewQuestion: IInterviewQuestion): void {
    const modalRef = this.modalService.open(InterviewQuestionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.interviewQuestion = interviewQuestion;
  }
}
