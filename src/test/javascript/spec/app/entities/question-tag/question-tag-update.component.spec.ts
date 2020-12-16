import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionTagUpdateComponent } from 'app/entities/question-tag/question-tag-update.component';
import { QuestionTagService } from 'app/entities/question-tag/question-tag.service';
import { QuestionTag } from 'app/shared/model/question-tag.model';

describe('Component Tests', () => {
  describe('QuestionTag Management Update Component', () => {
    let comp: QuestionTagUpdateComponent;
    let fixture: ComponentFixture<QuestionTagUpdateComponent>;
    let service: QuestionTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionTag(123);
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
        const entity = new QuestionTag();
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
