import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionSetDetailComponent } from 'app/entities/question-set/question-set-detail.component';
import { QuestionSet } from 'app/shared/model/question-set.model';

describe('Component Tests', () => {
  describe('QuestionSet Management Detail Component', () => {
    let comp: QuestionSetDetailComponent;
    let fixture: ComponentFixture<QuestionSetDetailComponent>;
    const route = ({ data: of({ questionSet: new QuestionSet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionSetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionSetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionSet on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionSet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
