import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterviewQuestion } from 'app/shared/model/interview-question.model';

@Component({
  selector: 'jhi-interview-question-detail',
  templateUrl: './interview-question-detail.component.html',
})
export class InterviewQuestionDetailComponent implements OnInit {
  interviewQuestion: IInterviewQuestion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewQuestion }) => (this.interviewQuestion = interviewQuestion));
  }

  previousState(): void {
    window.history.back();
  }
}
