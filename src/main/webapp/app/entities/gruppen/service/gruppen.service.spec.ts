import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGruppen, Gruppen } from '../gruppen.model';

import { GruppenService } from './gruppen.service';

describe('Service Tests', () => {
  describe('Gruppen Service', () => {
    let service: GruppenService;
    let httpMock: HttpTestingController;
    let elemDefault: IGruppen;
    let expectedResult: IGruppen | IGruppen[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(GruppenService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
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

      it('should create a Gruppen', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Gruppen()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Gruppen', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Gruppen', () => {
        const patchObject = Object.assign({}, new Gruppen());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Gruppen', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
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

      it('should delete a Gruppen', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addGruppenToCollectionIfMissing', () => {
        it('should add a Gruppen to an empty array', () => {
          const gruppen: IGruppen = { id: 123 };
          expectedResult = service.addGruppenToCollectionIfMissing([], gruppen);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(gruppen);
        });

        it('should not add a Gruppen to an array that contains it', () => {
          const gruppen: IGruppen = { id: 123 };
          const gruppenCollection: IGruppen[] = [
            {
              ...gruppen,
            },
            { id: 456 },
          ];
          expectedResult = service.addGruppenToCollectionIfMissing(gruppenCollection, gruppen);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Gruppen to an array that doesn't contain it", () => {
          const gruppen: IGruppen = { id: 123 };
          const gruppenCollection: IGruppen[] = [{ id: 456 }];
          expectedResult = service.addGruppenToCollectionIfMissing(gruppenCollection, gruppen);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(gruppen);
        });

        it('should add only unique Gruppen to an array', () => {
          const gruppenArray: IGruppen[] = [{ id: 123 }, { id: 456 }, { id: 65848 }];
          const gruppenCollection: IGruppen[] = [{ id: 123 }];
          expectedResult = service.addGruppenToCollectionIfMissing(gruppenCollection, ...gruppenArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const gruppen: IGruppen = { id: 123 };
          const gruppen2: IGruppen = { id: 456 };
          expectedResult = service.addGruppenToCollectionIfMissing([], gruppen, gruppen2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(gruppen);
          expect(expectedResult).toContain(gruppen2);
        });

        it('should accept null and undefined values', () => {
          const gruppen: IGruppen = { id: 123 };
          expectedResult = service.addGruppenToCollectionIfMissing([], null, gruppen, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(gruppen);
        });

        it('should return initial array if no Gruppen is added', () => {
          const gruppenCollection: IGruppen[] = [{ id: 123 }];
          expectedResult = service.addGruppenToCollectionIfMissing(gruppenCollection, undefined, null);
          expect(expectedResult).toEqual(gruppenCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
