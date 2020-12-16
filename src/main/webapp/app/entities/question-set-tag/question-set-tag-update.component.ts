import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionSetTag, QuestionSetTag } from 'app/shared/model/question-set-tag.model';
import { QuestionSetTagService } from './question-set-tag.service';
import { IQuestionSet } from 'app/shared/model/question-set.model';
import { QuestionSetService } from 'app/entities/question-set/question-set.service';
import { IQTag } from 'app/shared/model/q-tag.model';
import { QTagService } from 'app/entities/q-tag/q-tag.service';

type SelectableEntity = IQuestionSet | IQTag;

@Component({
  selector: 'jhi-question-set-tag-update',
  templateUrl: './question-set-tag-update.component.html',
})
export class QuestionSetTagUpdateComponent implements OnInit {
  isSaving = false;
  questionsets: IQuestionSet[] = [];
  qtags: IQTag[] = [];

  editForm = this.fb.group({
    id: [],
    qCount: [],
    questionset: [],
    qtag: [],
  });

  constructor(
    protected questionSetTagService: QuestionSetTagService,
    protected questionSetService: QuestionSetService,
    protected qTagService: QTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionSetTag }) => {
      this.updateForm(questionSetTag);

      this.questionSetService.query().subscribe((res: HttpResponse<IQuestionSet[]>) => (this.questionsets = res.body || []));

      this.qTagService.query().subscribe((res: HttpResponse<IQTag[]>) => (this.qtags = res.body || []));
    });
  }

  updateForm(questionSetTag: IQuestionSetTag): void {
    this.editForm.patchValue({
      id: questionSetTag.id,
      qCount: questionSetTag.qCount,
      questionset: questionSetTag.questionset,
      qtag: questionSetTag.qtag,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionSetTag = this.createFromForm();
    if (questionSetTag.id !== undefined) {
      this.subscribeToSaveResponse(this.questionSetTagService.update(questionSetTag));
    } else {
      this.subscribeToSaveResponse(this.questionSetTagService.create(questionSetTag));
    }
  }

  private createFromForm(): IQuestionSetTag {
    return {
      ...new QuestionSetTag(),
      id: this.editForm.get(['id'])!.value,
      qCount: this.editForm.get(['qCount'])!.value,
      questionset: this.editForm.get(['questionset'])!.value,
      qtag: this.editForm.get(['qtag'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionSetTag>>): void {
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
