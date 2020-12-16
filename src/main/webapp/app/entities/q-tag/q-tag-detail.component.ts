import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQTag } from 'app/shared/model/q-tag.model';

@Component({
  selector: 'jhi-q-tag-detail',
  templateUrl: './q-tag-detail.component.html',
})
export class QTagDetailComponent implements OnInit {
  qTag: IQTag | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qTag }) => (this.qTag = qTag));
  }

  previousState(): void {
    window.history.back();
  }
}
