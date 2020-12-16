import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { InterviewdbTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { QuestionSetTagDeleteDialogComponent } from 'app/entities/question-set-tag/question-set-tag-delete-dialog.component';
import { QuestionSetTagService } from 'app/entities/question-set-tag/question-set-tag.service';

describe('Component Tests', () => {
  describe('QuestionSetTag Management Delete Component', () => {
    let comp: QuestionSetTagDeleteDialogComponent;
    let fixture: ComponentFixture<QuestionSetTagDeleteDialogComponent>;
    let service: QuestionSetTagService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetTagDeleteDialogComponent],
      })
        .overrideTemplate(QuestionSetTagDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionSetTagDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSetTagService);
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
