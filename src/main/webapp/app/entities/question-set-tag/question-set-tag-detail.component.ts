import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionSetTag } from 'app/shared/model/question-set-tag.model';

@Component({
  selector: 'jhi-question-set-tag-detail',
  templateUrl: './question-set-tag-detail.component.html',
})
export class QuestionSetTagDetailComponent implements OnInit {
  questionSetTag: IQuestionSetTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionSetTag }) => (this.questionSetTag = questionSetTag));
  }

  previousState(): void {
    window.history.back();
  }
}
