import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPassen, Passen } from '../passen.model';

import { PassenService } from './passen.service';

describe('Service Tests', () => {
  describe('Passen Service', () => {
    let service: PassenService;
    let httpMock: HttpTestingController;
    let elemDefault: IPassen;
    let expectedResult: IPassen | IPassen[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PassenService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        p1: 0,
        p2: 0,
        p3: 0,
        p4: 0,
        p5: 0,
        p6: 0,
        p7: 0,
        p8: 0,
        p9: 0,
        p10: 0,
        resultat: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Passen', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Passen()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Passen', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            p1: 1,
            p2: 1,
            p3: 1,
            p4: 1,
            p5: 1,
            p6: 1,
            p7: 1,
            p8: 1,
            p9: 1,
            p10: 1,
            resultat: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Passen', () => {
        const patchObject = Object.assign(
          {
            p2: 1,
            p3: 1,
            p4: 1,
            p8: 1,
            p10: 1,
          },
          new Passen()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Passen', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            p1: 1,
            p2: 1,
            p3: 1,
            p4: 1,
            p5: 1,
            p6: 1,
            p7: 1,
            p8: 1,
            p9: 1,
            p10: 1,
            resultat: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Passen', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPassenToCollectionIfMissing', () => {
        it('should add a Passen to an empty array', () => {
          const passen: IPassen = { id: 123 };
          expectedResult = service.addPassenToCollectionIfMissing([], passen);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(passen);
        });

        it('should not add a Passen to an array that contains it', () => {
          const passen: IPassen = { id: 123 };
          const passenCollection: IPassen[] = [
            {
              ...passen,
            },
            { id: 456 },
          ];
          expectedResult = service.addPassenToCollectionIfMissing(passenCollection, passen);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Passen to an array that doesn't contain it", () => {
          const passen: IPassen = { id: 123 };
          const passenCollection: IPassen[] = [{ id: 456 }];
          expectedResult = service.addPassenToCollectionIfMissing(passenCollection, passen);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(passen);
        });

        it('should add only unique Passen to an array', () => {
          const passenArray: IPassen[] = [{ id: 123 }, { id: 456 }, { id: 57867 }];
          const passenCollection: IPassen[] = [{ id: 123 }];
          expectedResult = service.addPassenToCollectionIfMissing(passenCollection, ...passenArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const passen: IPassen = { id: 123 };
          const passen2: IPassen = { id: 456 };
          expectedResult = service.addPassenToCollectionIfMissing([], passen, passen2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(passen);
          expect(expectedResult).toContain(passen2);
        });

        it('should accept null and undefined values', () => {
          const passen: IPassen = { id: 123 };
          expectedResult = service.addPassenToCollectionIfMissing([], null, passen, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(passen);
        });

        it('should return initial array if no Passen is added', () => {
          const passenCollection: IPassen[] = [{ id: 123 }];
          expectedResult = service.addPassenToCollectionIfMissing(passenCollection, undefined, null);
          expect(expectedResult).toEqual(passenCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
