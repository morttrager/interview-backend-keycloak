import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { QuestionSetComponent } from 'app/entities/question-set/question-set.component';
import { QuestionSetService } from 'app/entities/question-set/question-set.service';
import { QuestionSet } from 'app/shared/model/question-set.model';

describe('Component Tests', () => {
  describe('QuestionSet Management Component', () => {
    let comp: QuestionSetComponent;
    let fixture: ComponentFixture<QuestionSetComponent>;
    let service: QuestionSetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QuestionSetComponent],
      })
        .overrideTemplate(QuestionSetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionSetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionSet(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionSets && comp.questionSets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
