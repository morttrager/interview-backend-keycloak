import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInterviewQuestionSet, InterviewQuestionSet } from 'app/shared/model/interview-question-set.model';
import { InterviewQuestionSetService } from './interview-question-set.service';
import { IQuestionSet } from 'app/shared/model/question-set.model';
import { QuestionSetService } from 'app/entities/question-set/question-set.service';
import { IInterviewee } from 'app/shared/model/interviewee.model';
import { IntervieweeService } from 'app/entities/interviewee/interviewee.service';

type SelectableEntity = IQuestionSet | IInterviewee;

@Component({
  selector: 'jhi-interview-question-set-update',
  templateUrl: './interview-question-set-update.component.html',
})
export class InterviewQuestionSetUpdateComponent implements OnInit {
  isSaving = false;
  questionsets: IQuestionSet[] = [];
  interviewees: IInterviewee[] = [];

  editForm = this.fb.group({
    id: [],
    active: [],
    abandoned: [],
    netScore: [],
    netQuestions: [],
    netWeight: [],
    time: [],
    questionset: [],
    interviewee: [],
  });

  constructor(
    protected interviewQuestionSetService: InterviewQuestionSetService,
    protected questionSetService: QuestionSetService,
    protected intervieweeService: IntervieweeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewQuestionSet }) => {
      if (!interviewQuestionSet.id) {
        const today = moment().startOf('day');
        interviewQuestionSet.time = today;
      }

      this.updateForm(interviewQuestionSet);

      this.questionSetService.query().subscribe((res: HttpResponse<IQuestionSet[]>) => (this.questionsets = res.body || []));

      this.intervieweeService.query().subscribe((res: HttpResponse<IInterviewee[]>) => (this.interviewees = res.body || []));
    });
  }

  updateForm(interviewQuestionSet: IInterviewQuestionSet): void {
    this.editForm.patchValue({
      id: interviewQuestionSet.id,
      active: interviewQuestionSet.active,
      abandoned: interviewQuestionSet.abandoned,
      netScore: interviewQuestionSet.netScore,
      netQuestions: interviewQuestionSet.netQuestions,
      netWeight: interviewQuestionSet.netWeight,
      time: interviewQuestionSet.time ? interviewQuestionSet.time.format(DATE_TIME_FORMAT) : null,
      questionset: interviewQuestionSet.questionset,
      interviewee: interviewQuestionSet.interviewee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewQuestionSet = this.createFromForm();
    if (interviewQuestionSet.id !== undefined) {
      this.subscribeToSaveResponse(this.interviewQuestionSetService.update(interviewQuestionSet));
    } else {
      this.subscribeToSaveResponse(this.interviewQuestionSetService.create(interviewQuestionSet));
    }
  }

  private createFromForm(): IInterviewQuestionSet {
    return {
      ...new InterviewQuestionSet(),
      id: this.editForm.get(['id'])!.value,
      active: this.editForm.get(['active'])!.value,
      abandoned: this.editForm.get(['abandoned'])!.value,
      netScore: this.editForm.get(['netScore'])!.value,
      netQuestions: this.editForm.get(['netQuestions'])!.value,
      netWeight: this.editForm.get(['netWeight'])!.value,
      time: this.editForm.get(['time'])!.value ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      questionset: this.editForm.get(['questionset'])!.value,
      interviewee: this.editForm.get(['interviewee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewQuestionSet>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
