/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CalciatoreService } from 'app/entities/calciatore/calciatore.service';
import { ICalciatore, Calciatore, Reparto, Ruolo, Selettore } from 'app/shared/model/calciatore.model';

describe('Service Tests', () => {
  describe('Calciatore Service', () => {
    let injector: TestBed;
    let service: CalciatoreService;
    let httpMock: HttpTestingController;
    let elemDefault: ICalciatore;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CalciatoreService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Calciatore(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        Reparto.DIFESA,
        Ruolo.POR,
        Selettore.DIFENSORE
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataNascita: currentDate.format(DATE_FORMAT)
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

      it('should create a Calciatore', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNascita: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascita: currentDate
          },
          returnedFromService
        );
        service
          .create(new Calciatore(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Calciatore', async () => {
        const returnedFromService = Object.assign(
          {
            codFiscale: 'BBBBBB',
            cognome: 'BBBBBB',
            nome: 'BBBBBB',
            dataNascita: currentDate.format(DATE_FORMAT),
            numTelefono: 'BBBBBB',
            email: 'BBBBBB',
            reparto: 'BBBBBB',
            ruolo: 'BBBBBB',
            selettore: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascita: currentDate
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

      it('should return a list of Calciatore', async () => {
        const returnedFromService = Object.assign(
          {
            codFiscale: 'BBBBBB',
            cognome: 'BBBBBB',
            nome: 'BBBBBB',
            dataNascita: currentDate.format(DATE_FORMAT),
            numTelefono: 'BBBBBB',
            email: 'BBBBBB',
            reparto: 'BBBBBB',
            ruolo: 'BBBBBB',
            selettore: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNascita: currentDate
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

      it('should delete a Calciatore', async () => {
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
