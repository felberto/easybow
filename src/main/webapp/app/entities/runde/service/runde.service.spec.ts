import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRunde, Runde } from '../runde.model';

import { RundeService } from './runde.service';

describe('Service Tests', () => {
  describe('Runde Service', () => {
    let service: RundeService;
    let httpMock: HttpTestingController;
    let elemDefault: IRunde;
    let expectedResult: IRunde | IRunde[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RundeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        runde: 0,
        datum: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Runde', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.create(new Runde()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Runde', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            runde: 1,
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Runde', () => {
        const patchObject = Object.assign(
          {
            runde: 1,
          },
          new Runde()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Runde', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            runde: 1,
            datum: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datum: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Runde', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRundeToCollectionIfMissing', () => {
        it('should add a Runde to an empty array', () => {
          const runde: IRunde = { id: 123 };
          expectedResult = service.addRundeToCollectionIfMissing([], runde);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(runde);
        });

        it('should not add a Runde to an array that contains it', () => {
          const runde: IRunde = { id: 123 };
          const rundeCollection: IRunde[] = [
            {
              ...runde,
            },
            { id: 456 },
          ];
          expectedResult = service.addRundeToCollectionIfMissing(rundeCollection, runde);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Runde to an array that doesn't contain it", () => {
          const runde: IRunde = { id: 123 };
          const rundeCollection: IRunde[] = [{ id: 456 }];
          expectedResult = service.addRundeToCollectionIfMissing(rundeCollection, runde);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(runde);
        });

        it('should add only unique Runde to an array', () => {
          const rundeArray: IRunde[] = [{ id: 123 }, { id: 456 }, { id: 50171 }];
          const rundeCollection: IRunde[] = [{ id: 123 }];
          expectedResult = service.addRundeToCollectionIfMissing(rundeCollection, ...rundeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const runde: IRunde = { id: 123 };
          const runde2: IRunde = { id: 456 };
          expectedResult = service.addRundeToCollectionIfMissing([], runde, runde2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(runde);
          expect(expectedResult).toContain(runde2);
        });

        it('should accept null and undefined values', () => {
          const runde: IRunde = { id: 123 };
          expectedResult = service.addRundeToCollectionIfMissing([], null, runde, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(runde);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
