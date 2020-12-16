import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { InterviewQuestionSetDetailComponent } from 'app/entities/interview-question-set/interview-question-set-detail.component';
import { InterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

describe('Component Tests', () => {
  describe('InterviewQuestionSet Management Detail Component', () => {
    let comp: InterviewQuestionSetDetailComponent;
    let fixture: ComponentFixture<InterviewQuestionSetDetailComponent>;
    const route = ({ data: of({ interviewQuestionSet: new InterviewQuestionSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [InterviewQuestionSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InterviewQuestionSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InterviewQuestionSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load interviewQuestionSet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.interviewQuestionSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
