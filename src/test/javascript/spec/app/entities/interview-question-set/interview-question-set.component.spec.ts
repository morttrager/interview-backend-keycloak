import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { InterviewQuestionSetComponent } from 'app/entities/interview-question-set/interview-question-set.component';
import { InterviewQuestionSetService } from 'app/entities/interview-question-set/interview-question-set.service';
import { InterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

describe('Component Tests', () => {
  describe('InterviewQuestionSet Management Component', () => {
    let comp: InterviewQuestionSetComponent;
    let fixture: ComponentFixture<InterviewQuestionSetComponent>;
    let service: InterviewQuestionSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionSetComponent],
      })
        .overrideTemplate(InterviewQuestionSetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InterviewQuestionSetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewQuestionSetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InterviewQuestionSet(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.interviewQuestionSets && comp.interviewQuestionSets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
