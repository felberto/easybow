import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResultate, Resultate } from '../resultate.model';

import { ResultateService } from './resultate.service';

describe('Service Tests', () => {
  describe('Resultate Service', () => {
    let service: ResultateService;
    let httpMock: HttpTestingController;
    let elemDefault: IResultate;
    let expectedResult: IResultate | IResultate[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ResultateService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        runde: 0,
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

      it('should create a Resultate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Resultate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Resultate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            runde: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Resultate', () => {
        const patchObject = Object.assign({}, new Resultate());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Resultate', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            runde: 1,
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

      it('should delete a Resultate', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addResultateToCollectionIfMissing', () => {
        it('should add a Resultate to an empty array', () => {
          const resultate: IResultate = { id: 123 };
          expectedResult = service.addResultateToCollectionIfMissing([], resultate);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(resultate);
        });

        it('should not add a Resultate to an array that contains it', () => {
          const resultate: IResultate = { id: 123 };
          const resultateCollection: IResultate[] = [
            {
              ...resultate,
            },
            { id: 456 },
          ];
          expectedResult = service.addResultateToCollectionIfMissing(resultateCollection, resultate);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Resultate to an array that doesn't contain it", () => {
          const resultate: IResultate = { id: 123 };
          const resultateCollection: IResultate[] = [{ id: 456 }];
          expectedResult = service.addResultateToCollectionIfMissing(resultateCollection, resultate);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(resultate);
        });

        it('should add only unique Resultate to an array', () => {
          const resultateArray: IResultate[] = [{ id: 123 }, { id: 456 }, { id: 39022 }];
          const resultateCollection: IResultate[] = [{ id: 123 }];
          expectedResult = service.addResultateToCollectionIfMissing(resultateCollection, ...resultateArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const resultate: IResultate = { id: 123 };
          const resultate2: IResultate = { id: 456 };
          expectedResult = service.addResultateToCollectionIfMissing([], resultate, resultate2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(resultate);
          expect(expectedResult).toContain(resultate2);
        });

        it('should accept null and undefined values', () => {
          const resultate: IResultate = { id: 123 };
          expectedResult = service.addResultateToCollectionIfMissing([], null, resultate, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(resultate);
        });

        it('should return initial array if no Resultate is added', () => {
          const resultateCollection: IResultate[] = [{ id: 123 }];
          expectedResult = service.addResultateToCollectionIfMissing(resultateCollection, undefined, null);
          expect(expectedResult).toEqual(resultateCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
