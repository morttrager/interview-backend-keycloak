import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionTag } from 'app/shared/model/question-tag.model';
import { QuestionTagService } from './question-tag.service';
import { QuestionTagDeleteDialogComponent } from './question-tag-delete-dialog.component';

@Component({
  selector: 'jhi-question-tag',
  templateUrl: './question-tag.component.html',
})
export class QuestionTagComponent implements OnInit, OnDestroy {
  questionTags?: IQuestionTag[];
  eventSubscriber?: Subscription;

  constructor(
    protected questionTagService: QuestionTagService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.questionTagService.query().subscribe((res: HttpResponse<IQuestionTag[]>) => (this.questionTags = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionTags();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionTag): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionTags(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionTagListModification', () => this.loadAll());
  }

  delete(questionTag: IQuestionTag): void {
    const modalRef = this.modalService.open(QuestionTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionTag = questionTag;
  }
}
