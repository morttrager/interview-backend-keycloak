import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InterviewQuestionSetService } from 'app/entities/interview-question-set/interview-question-set.service';
import { IInterviewQuestionSet, InterviewQuestionSet } from 'app/shared/model/interview-question-set.model';

describe('Service Tests', () => {
  describe('InterviewQuestionSet Service', () => {
    let injector: TestBed;
    let service: InterviewQuestionSetService;
    let httpMock: HttpTestingController;
    let elemDefault: IInterviewQuestionSet;
    let expectedResult: IInterviewQuestionSet | IInterviewQuestionSet[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InterviewQuestionSetService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new InterviewQuestionSet(0, false, false, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a InterviewQuestionSet', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );

        service.create(new InterviewQuestionSet()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a InterviewQuestionSet', () => {
        const returnedFromService = Object.assign(
          {
            active: true,
            abandoned: true,
            netScore: 1,
            netQuestions: 1,
            netWeight: 1,
            time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of InterviewQuestionSet', () => {
        const returnedFromService = Object.assign(
          {
            active: true,
            abandoned: true,
            netScore: 1,
            netQuestions: 1,
            netWeight: 1,
            time: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a InterviewQuestionSet', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
