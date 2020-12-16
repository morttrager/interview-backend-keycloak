import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInterviewQuestionSet } from 'app/shared/model/interview-question-set.model';
import { InterviewQuestionSetService } from './interview-question-set.service';
import { InterviewQuestionSetDeleteDialogComponent } from './interview-question-set-delete-dialog.component';

@Component({
  selector: 'jhi-interview-question-set',
  templateUrl: './interview-question-set.component.html',
})
export class InterviewQuestionSetComponent implements OnInit, OnDestroy {
  interviewQuestionSets?: IInterviewQuestionSet[];
  eventSubscriber?: Subscription;

  constructor(
    protected interviewQuestionSetService: InterviewQuestionSetService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.interviewQuestionSetService
      .query()
      .subscribe((res: HttpResponse<IInterviewQuestionSet[]>) => (this.interviewQuestionSets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInterviewQuestionSets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInterviewQuestionSet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInterviewQuestionSets(): void {
    this.eventSubscriber = this.eventManager.subscribe('interviewQuestionSetListModification', () => this.loadAll());
  }

  delete(interviewQuestionSet: IInterviewQuestionSet): void {
    const modalRef = this.modalService.open(InterviewQuestionSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.interviewQuestionSet = interviewQuestionSet;
  }
}
