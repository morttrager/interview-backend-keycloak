import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionSetTagDetailComponent } from 'app/entities/question-set-tag/question-set-tag-detail.component';
import { QuestionSetTag } from 'app/shared/model/question-set-tag.model';

describe('Component Tests', () => {
  describe('QuestionSetTag Management Detail Component', () => {
    let comp: QuestionSetTagDetailComponent;
    let fixture: ComponentFixture<QuestionSetTagDetailComponent>;
    const route = ({ data: of({ questionSetTag: new QuestionSetTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionSetTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionSetTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionSetTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionSetTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
