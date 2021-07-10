import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { Stellung } from 'app/entities/enumerations/stellung.model';
import { ISchuetze, Schuetze } from '../schuetze.model';

import { SchuetzeService } from './schuetze.service';

describe('Service Tests', () => {
  describe('Schuetze Service', () => {
    let service: SchuetzeService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchuetze;
    let expectedResult: ISchuetze | ISchuetze[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SchuetzeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        jahrgang: currentDate,
        stellung: Stellung.FREI,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            jahrgang: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Schuetze', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            jahrgang: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            jahrgang: currentDate,
          },
          returnedFromService
        );

        service.create(new Schuetze()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Schuetze', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            jahrgang: currentDate.format(DATE_FORMAT),
            stellung: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            jahrgang: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Schuetze', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Schuetze()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            jahrgang: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Schuetze', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            jahrgang: currentDate.format(DATE_FORMAT),
            stellung: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            jahrgang: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Schuetze', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSchuetzeToCollectionIfMissing', () => {
        it('should add a Schuetze to an empty array', () => {
          const schuetze: ISchuetze = { id: 123 };
          expectedResult = service.addSchuetzeToCollectionIfMissing([], schuetze);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(schuetze);
        });

        it('should not add a Schuetze to an array that contains it', () => {
          const schuetze: ISchuetze = { id: 123 };
          const schuetzeCollection: ISchuetze[] = [
            {
              ...schuetze,
            },
            { id: 456 },
          ];
          expectedResult = service.addSchuetzeToCollectionIfMissing(schuetzeCollection, schuetze);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Schuetze to an array that doesn't contain it", () => {
          const schuetze: ISchuetze = { id: 123 };
          const schuetzeCollection: ISchuetze[] = [{ id: 456 }];
          expectedResult = service.addSchuetzeToCollectionIfMissing(schuetzeCollection, schuetze);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(schuetze);
        });

        it('should add only unique Schuetze to an array', () => {
          const schuetzeArray: ISchuetze[] = [{ id: 123 }, { id: 456 }, { id: 75695 }];
          const schuetzeCollection: ISchuetze[] = [{ id: 123 }];
          expectedResult = service.addSchuetzeToCollectionIfMissing(schuetzeCollection, ...schuetzeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const schuetze: ISchuetze = { id: 123 };
          const schuetze2: ISchuetze = { id: 456 };
          expectedResult = service.addSchuetzeToCollectionIfMissing([], schuetze, schuetze2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(schuetze);
          expect(expectedResult).toContain(schuetze2);
        });

        it('should accept null and undefined values', () => {
          const schuetze: ISchuetze = { id: 123 };
          expectedResult = service.addSchuetzeToCollectionIfMissing([], null, schuetze, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(schuetze);
        });

        it('should return initial array if no Schuetze is added', () => {
          const schuetzeCollection: ISchuetze[] = [{ id: 123 }];
          expectedResult = service.addSchuetzeToCollectionIfMissing(schuetzeCollection, undefined, null);
          expect(expectedResult).toEqual(schuetzeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
