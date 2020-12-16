import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInterviewQuestion, InterviewQuestion } from 'app/shared/model/interview-question.model';
import { InterviewQuestionService } from './interview-question.service';
import { IInterviewee } from 'app/shared/model/interviewee.model';
import { IntervieweeService } from 'app/entities/interviewee/interviewee.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';
import { IInterviewQuestionSet } from 'app/shared/model/interview-question-set.model';
import { InterviewQuestionSetService } from 'app/entities/interview-question-set/interview-question-set.service';

type SelectableEntity = IInterviewee | IQuestion | IInterviewQuestionSet;

@Component({
  selector: 'jhi-interview-question-update',
  templateUrl: './interview-question-update.component.html',
})
export class InterviewQuestionUpdateComponent implements OnInit {
  isSaving = false;
  interviewees: IInterviewee[] = [];
  questions: IQuestion[] = [];
  interviewquestionsets: IInterviewQuestionSet[] = [];

  editForm = this.fb.group({
    id: [],
    score: [],
    respone: [null, [Validators.maxLength(8192)]],
    time: [],
    interviewee: [],
    question: [],
    interviewqs: [],
  });

  constructor(
    protected interviewQuestionService: InterviewQuestionService,
    protected intervieweeService: IntervieweeService,
    protected questionService: QuestionService,
    protected interviewQuestionSetService: InterviewQuestionSetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewQuestion }) => {
      if (!interviewQuestion.id) {
        const today = moment().startOf('day');
        interviewQuestion.time = today;
      }

      this.updateForm(interviewQuestion);

      this.intervieweeService.query().subscribe((res: HttpResponse<IInterviewee[]>) => (this.interviewees = res.body || []));

      this.questionService.query().subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body || []));

      this.interviewQuestionSetService
        .query()
        .subscribe((res: HttpResponse<IInterviewQuestionSet[]>) => (this.interviewquestionsets = res.body || []));
    });
  }

  updateForm(interviewQuestion: IInterviewQuestion): void {
    this.editForm.patchValue({
      id: interviewQuestion.id,
      score: interviewQuestion.score,
      respone: interviewQuestion.respone,
      time: interviewQuestion.time ? interviewQuestion.time.format(DATE_TIME_FORMAT) : null,
      interviewee: interviewQuestion.interviewee,
      question: interviewQuestion.question,
      interviewqs: interviewQuestion.interviewqs,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewQuestion = this.createFromForm();
    if (interviewQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.interviewQuestionService.update(interviewQuestion));
    } else {
      this.subscribeToSaveResponse(this.interviewQuestionService.create(interviewQuestion));
    }
  }

  private createFromForm(): IInterviewQuestion {
    return {
      ...new InterviewQuestion(),
      id: this.editForm.get(['id'])!.value,
      score: this.editForm.get(['score'])!.value,
      respone: this.editForm.get(['respone'])!.value,
      time: this.editForm.get(['time'])!.value ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      interviewee: this.editForm.get(['interviewee'])!.value,
      question: this.editForm.get(['question'])!.value,
      interviewqs: this.editForm.get(['interviewqs'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewQuestion>>): void {
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
