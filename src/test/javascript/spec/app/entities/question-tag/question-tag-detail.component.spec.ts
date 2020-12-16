import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionTagDetailComponent } from 'app/entities/question-tag/question-tag-detail.component';
import { QuestionTag } from 'app/shared/model/question-tag.model';

describe('Component Tests', () => {
  describe('QuestionTag Management Detail Component', () => {
    let comp: QuestionTagDetailComponent;
    let fixture: ComponentFixture<QuestionTagDetailComponent>;
    const route = ({ data: of({ questionTag: new QuestionTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
