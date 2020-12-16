import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionSetUpdateComponent } from 'app/entities/question-set/question-set-update.component';
import { QuestionSetService } from 'app/entities/question-set/question-set.service';
import { QuestionSet } from 'app/shared/model/question-set.model';

describe('Component Tests', () => {
  describe('QuestionSet Management Update Component', () => {
    let comp: QuestionSetUpdateComponent;
    let fixture: ComponentFixture<QuestionSetUpdateComponent>;
    let service: QuestionSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionSetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionSetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionSet(123);
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
        const entity = new QuestionSet();
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
