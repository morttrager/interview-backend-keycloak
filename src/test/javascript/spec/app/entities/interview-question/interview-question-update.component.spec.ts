import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { InterviewQuestionUpdateComponent } from 'app/entities/interview-question/interview-question-update.component';
import { InterviewQuestionService } from 'app/entities/interview-question/interview-question.service';
import { InterviewQuestion } from 'app/shared/model/interview-question.model';

describe('Component Tests', () => {
  describe('InterviewQuestion Management Update Component', () => {
    let comp: InterviewQuestionUpdateComponent;
    let fixture: ComponentFixture<InterviewQuestionUpdateComponent>;
    let service: InterviewQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InterviewQuestionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InterviewQuestionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewQuestionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InterviewQuestion(123);
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
        const entity = new InterviewQuestion();
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
