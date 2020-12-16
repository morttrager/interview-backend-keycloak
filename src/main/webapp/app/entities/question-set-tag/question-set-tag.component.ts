import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionSetTag } from 'app/shared/model/question-set-tag.model';
import { QuestionSetTagService } from './question-set-tag.service';
import { QuestionSetTagDeleteDialogComponent } from './question-set-tag-delete-dialog.component';

@Component({
  selector: 'jhi-question-set-tag',
  templateUrl: './question-set-tag.component.html',
})
export class QuestionSetTagComponent implements OnInit, OnDestroy {
  questionSetTags?: IQuestionSetTag[];
  eventSubscriber?: Subscription;

  constructor(
    protected questionSetTagService: QuestionSetTagService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.questionSetTagService.query().subscribe((res: HttpResponse<IQuestionSetTag[]>) => (this.questionSetTags = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionSetTags();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionSetTag): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionSetTags(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionSetTagListModification', () => this.loadAll());
  }

  delete(questionSetTag: IQuestionSetTag): void {
    const modalRef = this.modalService.open(QuestionSetTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionSetTag = questionSetTag;
  }
}
