import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionSetTagUpdateComponent } from 'app/entities/question-set-tag/question-set-tag-update.component';
import { QuestionSetTagService } from 'app/entities/question-set-tag/question-set-tag.service';
import { QuestionSetTag } from 'app/shared/model/question-set-tag.model';

describe('Component Tests', () => {
  describe('QuestionSetTag Management Update Component', () => {
    let comp: QuestionSetTagUpdateComponent;
    let fixture: ComponentFixture<QuestionSetTagUpdateComponent>;
    let service: QuestionSetTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionSetTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionSetTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSetTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionSetTag(123);
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
        const entity = new QuestionSetTag();
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
