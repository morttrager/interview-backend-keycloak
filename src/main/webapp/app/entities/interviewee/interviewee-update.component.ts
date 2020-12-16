import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInterviewee, Interviewee } from 'app/shared/model/interviewee.model';
import { IntervieweeService } from './interviewee.service';

@Component({
  selector: 'jhi-interviewee-update',
  templateUrl: './interviewee-update.component.html',
})
export class IntervieweeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    candidateId: [],
    remaining: [],
  });

  constructor(protected intervieweeService: IntervieweeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewee }) => {
      this.updateForm(interviewee);
    });
  }

  updateForm(interviewee: IInterviewee): void {
    this.editForm.patchValue({
      id: interviewee.id,
      candidateId: interviewee.candidateId,
      remaining: interviewee.remaining,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewee = this.createFromForm();
    if (interviewee.id !== undefined) {
      this.subscribeToSaveResponse(this.intervieweeService.update(interviewee));
    } else {
      this.subscribeToSaveResponse(this.intervieweeService.create(interviewee));
    }
  }

  private createFromForm(): IInterviewee {
    return {
      ...new Interviewee(),
      id: this.editForm.get(['id'])!.value,
      candidateId: this.editForm.get(['candidateId'])!.value,
      remaining: this.editForm.get(['remaining'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewee>>): void {
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
