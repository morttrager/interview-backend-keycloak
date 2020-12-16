import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQTag, QTag } from 'app/shared/model/q-tag.model';
import { QTagService } from './q-tag.service';

@Component({
  selector: 'jhi-q-tag-update',
  templateUrl: './q-tag-update.component.html',
})
export class QTagUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected qTagService: QTagService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qTag }) => {
      this.updateForm(qTag);
    });
  }

  updateForm(qTag: IQTag): void {
    this.editForm.patchValue({
      id: qTag.id,
      name: qTag.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qTag = this.createFromForm();
    if (qTag.id !== undefined) {
      this.subscribeToSaveResponse(this.qTagService.update(qTag));
    } else {
      this.subscribeToSaveResponse(this.qTagService.create(qTag));
    }
  }

  private createFromForm(): IQTag {
    return {
      ...new QTag(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQTag>>): void {
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
