import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionSet } from 'app/shared/model/question-set.model';

@Component({
  selector: 'jhi-question-set-detail',
  templateUrl: './question-set-detail.component.html',
})
export class QuestionSetDetailComponent implements OnInit {
  questionSet: IQuestionSet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionSet }) => (this.questionSet = questionSet));
  }

  previousState(): void {
    window.history.back();
  }
}
