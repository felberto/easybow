import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Rangierungskriterien } from 'app/entities/enumerations/rangierungskriterien.model';
import { IRangierung, Rangierung } from '../rangierung.model';

import { RangierungService } from './rangierung.service';

describe('Service Tests', () => {
  describe('Rangierung Service', () => {
    let service: RangierungService;
    let httpMock: HttpTestingController;
    let elemDefault: IRangierung;
    let expectedResult: IRangierung | IRangierung[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RangierungService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        position: 0,
        rangierungskriterien: Rangierungskriterien.RESULTAT,
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

      it('should create a Rangierung', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Rangierung()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Rangierung', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            position: 1,
            rangierungskriterien: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Rangierung', () => {
        const patchObject = Object.assign(
          {
            position: 1,
            rangierungskriterien: 'BBBBBB',
          },
          new Rangierung()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Rangierung', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            position: 1,
            rangierungskriterien: 'BBBBBB',
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

      it('should delete a Rangierung', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRangierungToCollectionIfMissing', () => {
        it('should add a Rangierung to an empty array', () => {
          const rangierung: IRangierung = { id: 123 };
          expectedResult = service.addRangierungToCollectionIfMissing([], rangierung);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rangierung);
        });

        it('should not add a Rangierung to an array that contains it', () => {
          const rangierung: IRangierung = { id: 123 };
          const rangierungCollection: IRangierung[] = [
            {
              ...rangierung,
            },
            { id: 456 },
          ];
          expectedResult = service.addRangierungToCollectionIfMissing(rangierungCollection, rangierung);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Rangierung to an array that doesn't contain it", () => {
          const rangierung: IRangierung = { id: 123 };
          const rangierungCollection: IRangierung[] = [{ id: 456 }];
          expectedResult = service.addRangierungToCollectionIfMissing(rangierungCollection, rangierung);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rangierung);
        });

        it('should add only unique Rangierung to an array', () => {
          const rangierungArray: IRangierung[] = [{ id: 123 }, { id: 456 }, { id: 78486 }];
          const rangierungCollection: IRangierung[] = [{ id: 123 }];
          expectedResult = service.addRangierungToCollectionIfMissing(rangierungCollection, ...rangierungArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const rangierung: IRangierung = { id: 123 };
          const rangierung2: IRangierung = { id: 456 };
          expectedResult = service.addRangierungToCollectionIfMissing([], rangierung, rangierung2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rangierung);
          expect(expectedResult).toContain(rangierung2);
        });

        it('should accept null and undefined values', () => {
          const rangierung: IRangierung = { id: 123 };
          expectedResult = service.addRangierungToCollectionIfMissing([], null, rangierung, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rangierung);
        });

        it('should return initial array if no Rangierung is added', () => {
          const rangierungCollection: IRangierung[] = [{ id: 123 }];
          expectedResult = service.addRangierungToCollectionIfMissing(rangierungCollection, undefined, null);
          expect(expectedResult).toEqual(rangierungCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
