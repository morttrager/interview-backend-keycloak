import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInterviewee } from 'app/shared/model/interviewee.model';
import { IntervieweeService } from './interviewee.service';
import { IntervieweeDeleteDialogComponent } from './interviewee-delete-dialog.component';

@Component({
  selector: 'jhi-interviewee',
  templateUrl: './interviewee.component.html',
})
export class IntervieweeComponent implements OnInit, OnDestroy {
  interviewees?: IInterviewee[];
  eventSubscriber?: Subscription;

  constructor(
    protected intervieweeService: IntervieweeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.intervieweeService.query().subscribe((res: HttpResponse<IInterviewee[]>) => (this.interviewees = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInterviewees();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInterviewee): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInterviewees(): void {
    this.eventSubscriber = this.eventManager.subscribe('intervieweeListModification', () => this.loadAll());
  }

  delete(interviewee: IInterviewee): void {
    const modalRef = this.modalService.open(IntervieweeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.interviewee = interviewee;
  }
}
