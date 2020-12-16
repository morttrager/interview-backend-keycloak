import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionTagComponent } from 'app/entities/question-tag/question-tag.component';
import { QuestionTagService } from 'app/entities/question-tag/question-tag.service';
import { QuestionTag } from 'app/shared/model/question-tag.model';

describe('Component Tests', () => {
  describe('QuestionTag Management Component', () => {
    let comp: QuestionTagComponent;
    let fixture: ComponentFixture<QuestionTagComponent>;
    let service: QuestionTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionTagComponent],
      })
        .overrideTemplate(QuestionTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionTag(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionTags && comp.questionTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
