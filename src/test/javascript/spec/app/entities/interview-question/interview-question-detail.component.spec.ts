import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { InterviewQuestionDetailComponent } from 'app/entities/interview-question/interview-question-detail.component';
import { InterviewQuestion } from 'app/shared/model/interview-question.model';

describe('Component Tests', () => {
  describe('InterviewQuestion Management Detail Component', () => {
    let comp: InterviewQuestionDetailComponent;
    let fixture: ComponentFixture<InterviewQuestionDetailComponent>;
    const route = ({ data: of({ interviewQuestion: new InterviewQuestion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InterviewQuestionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InterviewQuestionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load interviewQuestion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.interviewQuestion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
