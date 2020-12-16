import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { IntervieweeDetailComponent } from 'app/entities/interviewee/interviewee-detail.component';
import { Interviewee } from 'app/shared/model/interviewee.model';

describe('Component Tests', () => {
  describe('Interviewee Management Detail Component', () => {
    let comp: IntervieweeDetailComponent;
    let fixture: ComponentFixture<IntervieweeDetailComponent>;
    const route = ({ data: of({ interviewee: new Interviewee(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [IntervieweeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IntervieweeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntervieweeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load interviewee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.interviewee).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
