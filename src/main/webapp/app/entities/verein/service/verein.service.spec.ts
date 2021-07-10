import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVerein, Verein } from '../verein.model';

import { VereinService } from './verein.service';

describe('Service Tests', () => {
  describe('Verein Service', () => {
    let service: VereinService;
    let httpMock: HttpTestingController;
    let elemDefault: IVerein;
    let expectedResult: IVerein | IVerein[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VereinService);
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

      it('should create a Verein', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Verein()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Verein', () => {
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

      it('should partial update a Verein', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Verein()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Verein', () => {
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

      it('should delete a Verein', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVereinToCollectionIfMissing', () => {
        it('should add a Verein to an empty array', () => {
          const verein: IVerein = { id: 123 };
          expectedResult = service.addVereinToCollectionIfMissing([], verein);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(verein);
        });

        it('should not add a Verein to an array that contains it', () => {
          const verein: IVerein = { id: 123 };
          const vereinCollection: IVerein[] = [
            {
              ...verein,
            },
            { id: 456 },
          ];
          expectedResult = service.addVereinToCollectionIfMissing(vereinCollection, verein);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Verein to an array that doesn't contain it", () => {
          const verein: IVerein = { id: 123 };
          const vereinCollection: IVerein[] = [{ id: 456 }];
          expectedResult = service.addVereinToCollectionIfMissing(vereinCollection, verein);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(verein);
        });

        it('should add only unique Verein to an array', () => {
          const vereinArray: IVerein[] = [{ id: 123 }, { id: 456 }, { id: 31174 }];
          const vereinCollection: IVerein[] = [{ id: 123 }];
          expectedResult = service.addVereinToCollectionIfMissing(vereinCollection, ...vereinArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const verein: IVerein = { id: 123 };
          const verein2: IVerein = { id: 456 };
          expectedResult = service.addVereinToCollectionIfMissing([], verein, verein2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(verein);
          expect(expectedResult).toContain(verein2);
        });

        it('should accept null and undefined values', () => {
          const verein: IVerein = { id: 123 };
          expectedResult = service.addVereinToCollectionIfMissing([], null, verein, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(verein);
        });

        it('should return initial array if no Verein is added', () => {
          const vereinCollection: IVerein[] = [{ id: 123 }];
          expectedResult = service.addVereinToCollectionIfMissing(vereinCollection, undefined, null);
          expect(expectedResult).toEqual(vereinCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
