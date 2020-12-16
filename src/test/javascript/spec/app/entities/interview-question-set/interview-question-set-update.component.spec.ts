import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { InterviewQuestionSetUpdateComponent } from 'app/entities/interview-question-set/interview-question-set-update.component';
import { InterviewQuestionSetService } from 'app/entities/interview-question-set/interview-question-set.service';
import { InterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

describe('Component Tests', () => {
  describe('InterviewQuestionSet Management Update Component', () => {
    let comp: InterviewQuestionSetUpdateComponent;
    let fixture: ComponentFixture<InterviewQuestionSetUpdateComponent>;
    let service: InterviewQuestionSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionSetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InterviewQuestionSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InterviewQuestionSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewQuestionSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InterviewQuestionSet(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new InterviewQuestionSet();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
