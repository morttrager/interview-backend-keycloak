import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { IntervieweeUpdateComponent } from 'app/entities/interviewee/interviewee-update.component';
import { IntervieweeService } from 'app/entities/interviewee/interviewee.service';
import { Interviewee } from 'app/shared/model/interviewee.model';

describe('Component Tests', () => {
  describe('Interviewee Management Update Component', () => {
    let comp: IntervieweeUpdateComponent;
    let fixture: ComponentFixture<IntervieweeUpdateComponent>;
    let service: IntervieweeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [IntervieweeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IntervieweeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntervieweeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntervieweeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Interviewee(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Interviewee();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
