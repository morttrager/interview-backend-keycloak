import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionSetTagComponent } from 'app/entities/question-set-tag/question-set-tag.component';
import { QuestionSetTagService } from 'app/entities/question-set-tag/question-set-tag.service';
import { QuestionSetTag } from 'app/shared/model/question-set-tag.model';

describe('Component Tests', () => {
  describe('QuestionSetTag Management Component', () => {
    let comp: QuestionSetTagComponent;
    let fixture: ComponentFixture<QuestionSetTagComponent>;
    let service: QuestionSetTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetTagComponent],
      })
        .overrideTemplate(QuestionSetTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionSetTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSetTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionSetTag(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionSetTags && comp.questionSetTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
