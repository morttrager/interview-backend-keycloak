import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { InterviewQuestionComponent } from 'app/entities/interview-question/interview-question.component';
import { InterviewQuestionService } from 'app/entities/interview-question/interview-question.service';
import { InterviewQuestion } from 'app/shared/model/interview-question.model';

describe('Component Tests', () => {
  describe('InterviewQuestion Management Component', () => {
    let comp: InterviewQuestionComponent;
    let fixture: ComponentFixture<InterviewQuestionComponent>;
    let service: InterviewQuestionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionComponent],
      })
        .overrideTemplate(InterviewQuestionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InterviewQuestionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewQuestionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InterviewQuestion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.interviewQuestions && comp.interviewQuestions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
