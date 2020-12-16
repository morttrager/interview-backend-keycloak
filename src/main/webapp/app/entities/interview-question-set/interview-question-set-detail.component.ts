import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

@Component({
  selector: 'jhi-interview-question-set-detail',
  templateUrl: './interview-question-set-detail.component.html',
})
export class InterviewQuestionSetDetailComponent implements OnInit {
  interviewQuestionSet: IInterviewQuestionSet | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewQuestionSet }) => (this.interviewQuestionSet = interviewQuestionSet));
  }

  previousState(): void {
    window.history.back();
  }
}
