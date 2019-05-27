/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ParametriFisiciService } from 'app/entities/parametri-fisici/parametri-fisici.service';
import { IParametriFisici, ParametriFisici, Stato } from 'app/shared/model/parametri-fisici.model';

describe('Service Tests', () => {
  describe('ParametriFisici Service', () => {
    let injector: TestBed;
    let service: ParametriFisiciService;
    let httpMock: HttpTestingController;
    let elemDefault: IParametriFisici;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ParametriFisiciService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ParametriFisici(0, currentDate, 0, 0, 0, Stato.SOTTOPESO, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataRivelazione: currentDate.format(DATE_FORMAT)
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

      it('should create a ParametriFisici', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataRivelazione: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRivelazione: currentDate
          },
          returnedFromService
        );
        service
          .create(new ParametriFisici(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ParametriFisici', async () => {
        const returnedFromService = Object.assign(
          {
            dataRivelazione: currentDate.format(DATE_FORMAT),
            massaCorporea: 1,
            altezza: 1,
            bmi: 1,
            condizione: 'BBBBBB',
            fcRiposo: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataRivelazione: currentDate
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

      it('should return a list of ParametriFisici', async () => {
        const returnedFromService = Object.assign(
          {
            dataRivelazione: currentDate.format(DATE_FORMAT),
            massaCorporea: 1,
            altezza: 1,
            bmi: 1,
            condizione: 'BBBBBB',
            fcRiposo: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataRivelazione: currentDate
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

      it('should delete a ParametriFisici', async () => {
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
