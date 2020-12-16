import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionSet, QuestionSet } from 'app/shared/model/question-set.model';
import { QuestionSetService } from './question-set.service';

@Component({
  selector: 'jhi-question-set-update',
  templateUrl: './question-set-update.component.html',
})
export class QuestionSetUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    intro: [null, [Validators.maxLength(8192)]],
    outro: [null, [Validators.maxLength(8192)]],
  });

  constructor(protected questionSetService: QuestionSetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionSet }) => {
      this.updateForm(questionSet);
    });
  }

  updateForm(questionSet: IQuestionSet): void {
    this.editForm.patchValue({
      id: questionSet.id,
      name: questionSet.name,
      intro: questionSet.intro,
      outro: questionSet.outro,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionSet = this.createFromForm();
    if (questionSet.id !== undefined) {
      this.subscribeToSaveResponse(this.questionSetService.update(questionSet));
    } else {
      this.subscribeToSaveResponse(this.questionSetService.create(questionSet));
    }
  }

  private createFromForm(): IQuestionSet {
    return {
      ...new QuestionSet(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      intro: this.editForm.get(['intro'])!.value,
      outro: this.editForm.get(['outro'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionSet>>): void {
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
}
