/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { Prova1500mService } from 'app/entities/prova-1500-m/prova-1500-m.service';
import { IProva1500m, Prova1500m, Feedback } from 'app/shared/model/prova-1500-m.model';

describe('Service Tests', () => {
  describe('Prova1500m Service', () => {
    let injector: TestBed;
    let service: Prova1500mService;
    let httpMock: HttpTestingController;
    let elemDefault: IProva1500m;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(Prova1500mService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Prova1500m(0, currentDate, 0, 0, Feedback.SCARSO, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataProva: currentDate.format(DATE_FORMAT)
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

      it('should create a Prova1500m', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataProva: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataProva: currentDate
          },
          returnedFromService
        );
        service
          .create(new Prova1500m(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Prova1500m', async () => {
        const returnedFromService = Object.assign(
          {
            dataProva: currentDate.format(DATE_FORMAT),
            tempo: 1,
            tempoKm: 1,
            commento: 'BBBBBB',
            condClimatiche: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataProva: currentDate
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

      it('should return a list of Prova1500m', async () => {
        const returnedFromService = Object.assign(
          {
            dataProva: currentDate.format(DATE_FORMAT),
            tempo: 1,
            tempoKm: 1,
            commento: 'BBBBBB',
            condClimatiche: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataProva: currentDate
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

      it('should delete a Prova1500m', async () => {
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
