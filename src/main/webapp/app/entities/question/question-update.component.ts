import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html',
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    question: [null, [Validators.maxLength(8192)]],
    answer: [null, [Validators.maxLength(8192)]],
    weight: [],
  });

  constructor(protected questionService: QuestionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);
    });
  }

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      question: question.question,
      answer: question.answer,
      weight: question.weight,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      answer: this.editForm.get(['answer'])!.value,
      weight: this.editForm.get(['weight'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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
