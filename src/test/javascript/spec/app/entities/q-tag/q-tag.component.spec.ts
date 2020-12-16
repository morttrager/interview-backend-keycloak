import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { InterviewdbTestModule } from '../../../test.module';
import { QTagComponent } from 'app/entities/q-tag/q-tag.component';
import { QTagService } from 'app/entities/q-tag/q-tag.service';
import { QTag } from 'app/shared/model/q-tag.model';

describe('Component Tests', () => {
  describe('QTag Management Component', () => {
    let comp: QTagComponent;
    let fixture: ComponentFixture<QTagComponent>;
    let service: QTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QTagComponent],
      })
        .overrideTemplate(QTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QTag(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.qTags && comp.qTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
