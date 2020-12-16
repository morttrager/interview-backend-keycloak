import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QTagUpdateComponent } from 'app/entities/q-tag/q-tag-update.component';
import { QTagService } from 'app/entities/q-tag/q-tag.service';
import { QTag } from 'app/shared/model/q-tag.model';

describe('Component Tests', () => {
  describe('QTag Management Update Component', () => {
    let comp: QTagUpdateComponent;
    let fixture: ComponentFixture<QTagUpdateComponent>;
    let service: QTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QTagUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QTag(123);
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
        const entity = new QTag();
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
