import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InterviewdbTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { InterviewQuestionSetDeleteDialogComponent } from 'app/entities/interview-question-set/interview-question-set-delete-dialog.component';
import { InterviewQuestionSetService } from 'app/entities/interview-question-set/interview-question-set.service';

describe('Component Tests', () => {
  describe('InterviewQuestionSet Management Delete Component', () => {
    let comp: InterviewQuestionSetDeleteDialogComponent;
    let fixture: ComponentFixture<InterviewQuestionSetDeleteDialogComponent>;
    let service: InterviewQuestionSetService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionSetDeleteDialogComponent],
      })
        .overrideTemplate(InterviewQuestionSetDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InterviewQuestionSetDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewQuestionSetService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
