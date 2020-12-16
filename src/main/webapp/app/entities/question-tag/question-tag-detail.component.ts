import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionTag } from 'app/shared/model/question-tag.model';

@Component({
  selector: 'jhi-question-tag-detail',
  templateUrl: './question-tag-detail.component.html',
})
export class QuestionTagDetailComponent implements OnInit {
  questionTag: IQuestionTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionTag }) => (this.questionTag = questionTag));
  }

  previousState(): void {
    window.history.back();
  }
}
