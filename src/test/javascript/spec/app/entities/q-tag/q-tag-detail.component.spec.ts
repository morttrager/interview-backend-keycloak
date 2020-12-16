import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InterviewdbTestModule } from '../../../test.module';
import { QTagDetailComponent } from 'app/entities/q-tag/q-tag-detail.component';
import { QTag } from 'app/shared/model/q-tag.model';

describe('Component Tests', () => {
  describe('QTag Management Detail Component', () => {
    let comp: QTagDetailComponent;
    let fixture: ComponentFixture<QTagDetailComponent>;
    const route = ({ data: of({ qTag: new QTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [InterviewdbTestModule],
        declarations: [QTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load qTag on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
