import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterviewee } from 'app/shared/model/interviewee.model';

@Component({
  selector: 'jhi-interviewee-detail',
  templateUrl: './interviewee-detail.component.html',
})
export class IntervieweeDetailComponent implements OnInit {
  interviewee: IInterviewee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewee }) => (this.interviewee = interviewee));
  }

  previousState(): void {
    window.history.back();
  }
}
