import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQTag } from 'app/shared/model/q-tag.model';
import { QTagService } from './q-tag.service';
import { QTagDeleteDialogComponent } from './q-tag-delete-dialog.component';

@Component({
  selector: 'jhi-q-tag',
  templateUrl: './q-tag.component.html',
})
export class QTagComponent implements OnInit, OnDestroy {
  qTags?: IQTag[];
  eventSubscriber?: Subscription;

  constructor(protected qTagService: QTagService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.qTagService.query().subscribe((res: HttpResponse<IQTag[]>) => (this.qTags = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQTags();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQTag): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQTags(): void {
    this.eventSubscriber = this.eventManager.subscribe('qTagListModification', () => this.loadAll());
  }

  delete(qTag: IQTag): void {
    const modalRef = this.modalService.open(QTagDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.qTag = qTag;
  }
}
