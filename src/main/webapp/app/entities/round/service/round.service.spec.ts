import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRound, Round } from '../round.model';

import { RoundService } from './round.service';

describe('Service Tests', () => {
  describe('Round Service', () => {
    let service: RoundService;
    let httpMock: HttpTestingController;
    let elemDefault: IRound;
    let expectedResult: IRound | IRound[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RoundService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        round: 0,
        date: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Round', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.create(new Round()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Round', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            round: 1,
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Round', () => {
        const patchObject = Object.assign(
          {
            round: 1,
          },
          new Round()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Round', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            round: 1,
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Round', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRoundToCollectionIfMissing', () => {
        it('should add a Round to an empty array', () => {
          const round: IRound = { id: 123 };
          expectedResult = service.addRoundToCollectionIfMissing([], round);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(round);
        });

        it('should not add a Round to an array that contains it', () => {
          const round: IRound = { id: 123 };
          const roundCollection: IRound[] = [
            {
              ...round,
            },
            { id: 456 },
          ];
          expectedResult = service.addRoundToCollectionIfMissing(roundCollection, round);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Round to an array that doesn't contain it", () => {
          const round: IRound = { id: 123 };
          const roundCollection: IRound[] = [{ id: 456 }];
          expectedResult = service.addRoundToCollectionIfMissing(roundCollection, round);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(round);
        });

        it('should add only unique Round to an array', () => {
          const roundArray: IRound[] = [{ id: 123 }, { id: 456 }, { id: 50171 }];
          const roundCollection: IRound[] = [{ id: 123 }];
          expectedResult = service.addRoundToCollectionIfMissing(roundCollection, ...roundArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const round: IRound = { id: 123 };
          const round2: IRound = { id: 456 };
          expectedResult = service.addRoundToCollectionIfMissing([], round, round2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(round);
          expect(expectedResult).toContain(round2);
        });

        it('should accept null and undefined values', () => {
          const round: IRound = { id: 123 };
          expectedResult = service.addRoundToCollectionIfMissing([], null, round, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(round);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
