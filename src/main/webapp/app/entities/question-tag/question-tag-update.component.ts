import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionTag, QuestionTag } from 'app/shared/model/question-tag.model';
import { QuestionTagService } from './question-tag.service';
import { IQTag } from 'app/shared/model/q-tag.model';
import { QTagService } from 'app/entities/q-tag/q-tag.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';

type SelectableEntity = IQTag | IQuestion;

@Component({
  selector: 'jhi-question-tag-update',
  templateUrl: './question-tag-update.component.html',
})
export class QuestionTagUpdateComponent implements OnInit {
  isSaving = false;
  qtags: IQTag[] = [];
  questions: IQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    qtag: [],
    question: [],
  });

  constructor(
    protected questionTagService: QuestionTagService,
    protected qTagService: QTagService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionTag }) => {
      this.updateForm(questionTag);

      this.qTagService.query().subscribe((res: HttpResponse<IQTag[]>) => (this.qtags = res.body || []));

      this.questionService.query().subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body || []));
    });
  }

  updateForm(questionTag: IQuestionTag): void {
    this.editForm.patchValue({
      id: questionTag.id,
      qtag: questionTag.qtag,
      question: questionTag.question,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionTag = this.createFromForm();
    if (questionTag.id !== undefined) {
      this.subscribeToSaveResponse(this.questionTagService.update(questionTag));
    } else {
      this.subscribeToSaveResponse(this.questionTagService.create(questionTag));
    }
  }

  private createFromForm(): IQuestionTag {
    return {
      ...new QuestionTag(),
      id: this.editForm.get(['id'])!.value,
      qtag: this.editForm.get(['qtag'])!.value,
      question: this.editForm.get(['question'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionTag>>): void {
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
