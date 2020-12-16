import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionSet } from 'app/shared/model/question-set.model';
import { QuestionSetService } from './question-set.service';
import { QuestionSetDeleteDialogComponent } from './question-set-delete-dialog.component';

@Component({
  selector: 'jhi-question-set',
  templateUrl: './question-set.component.html',
})
export class QuestionSetComponent implements OnInit, OnDestroy {
  questionSets?: IQuestionSet[];
  eventSubscriber?: Subscription;

  constructor(
    protected questionSetService: QuestionSetService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.questionSetService.query().subscribe((res: HttpResponse<IQuestionSet[]>) => (this.questionSets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionSets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionSet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionSets(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionSetListModification', () => this.loadAll());
  }

  delete(questionSet: IQuestionSet): void {
    const modalRef = this.modalService.open(QuestionSetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionSet = questionSet;
  }
}
