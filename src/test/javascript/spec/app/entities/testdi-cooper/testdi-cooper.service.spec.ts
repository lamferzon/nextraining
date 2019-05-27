/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TestdiCooperService } from 'app/entities/testdi-cooper/testdi-cooper.service';
import { ITestdiCooper, TestdiCooper, Feedback } from 'app/shared/model/testdi-cooper.model';

describe('Service Tests', () => {
  describe('TestdiCooper Service', () => {
    let injector: TestBed;
    let service: TestdiCooperService;
    let httpMock: HttpTestingController;
    let elemDefault: ITestdiCooper;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(TestdiCooperService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TestdiCooper(0, currentDate, 0, 0, Feedback.SCARSO, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataTest: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a TestdiCooper', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataTest: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataTest: currentDate
          },
          returnedFromService
        );
        service
          .create(new TestdiCooper(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a TestdiCooper', async () => {
        const returnedFromService = Object.assign(
          {
            dataTest: currentDate.format(DATE_FORMAT),
            distanza: 1,
            v02Max: 1,
            commento: 'BBBBBB',
            condClimatiche: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTest: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of TestdiCooper', async () => {
        const returnedFromService = Object.assign(
          {
            dataTest: currentDate.format(DATE_FORMAT),
            distanza: 1,
            v02Max: 1,
            commento: 'BBBBBB',
            condClimatiche: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataTest: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TestdiCooper', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

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
