import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { IntervieweeComponent } from 'app/entities/interviewee/interviewee.component';
import { IntervieweeService } from 'app/entities/interviewee/interviewee.service';
import { Interviewee } from 'app/shared/model/interviewee.model';

describe('Component Tests', () => {
  describe('Interviewee Management Component', () => {
    let comp: IntervieweeComponent;
    let fixture: ComponentFixture<IntervieweeComponent>;
    let service: IntervieweeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [IntervieweeComponent],
      })
        .overrideTemplate(IntervieweeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntervieweeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntervieweeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Interviewee(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.interviewees && comp.interviewees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
