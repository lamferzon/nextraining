/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InfortunioService } from 'app/entities/infortunio/infortunio.service';
import { IInfortunio, Infortunio, Gravita } from 'app/shared/model/infortunio.model';

describe('Service Tests', () => {
  describe('Infortunio Service', () => {
    let injector: TestBed;
    let service: InfortunioService;
    let httpMock: HttpTestingController;
    let elemDefault: IInfortunio;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InfortunioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Infortunio(0, currentDate, currentDate, Gravita.UNO, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataInizio: currentDate.format(DATE_FORMAT),
            dataFine: currentDate.format(DATE_FORMAT)
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

      it('should create a Infortunio', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInizio: currentDate.format(DATE_FORMAT),
            dataFine: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInizio: currentDate,
            dataFine: currentDate
          },
          returnedFromService
        );
        service
          .create(new Infortunio(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Infortunio', async () => {
        const returnedFromService = Object.assign(
          {
            dataInizio: currentDate.format(DATE_FORMAT),
            dataFine: currentDate.format(DATE_FORMAT),
            gravita: 'BBBBBB',
            descrizione: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInizio: currentDate,
            dataFine: currentDate
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

      it('should return a list of Infortunio', async () => {
        const returnedFromService = Object.assign(
          {
            dataInizio: currentDate.format(DATE_FORMAT),
            dataFine: currentDate.format(DATE_FORMAT),
            gravita: 'BBBBBB',
            descrizione: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInizio: currentDate,
            dataFine: currentDate
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

      it('should delete a Infortunio', async () => {
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
