import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IResults, Results } from '../results.model';

import { ResultsService } from './results.service';

describe('Service Tests', () => {
  describe('Results Service', () => {
    let service: ResultsService;
    let httpMock: HttpTestingController;
    let elemDefault: IResults;
    let expectedResult: IResults | IResults[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ResultsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        round: 0,
        result: 0,
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

      it('should create a Results', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Results()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Results', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            round: 1,
            result: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Results', () => {
        const patchObject = Object.assign({}, new Results());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Results', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            round: 1,
            result: 1,
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

      it('should delete a Results', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addResultsToCollectionIfMissing', () => {
        it('should add a Results to an empty array', () => {
          const results: IResults = { id: 123 };
          expectedResult = service.addResultsToCollectionIfMissing([], results);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(results);
        });

        it('should not add a Results to an array that contains it', () => {
          const results: IResults = { id: 123 };
          const resultsCollection: IResults[] = [
            {
              ...results,
            },
            { id: 456 },
          ];
          expectedResult = service.addResultsToCollectionIfMissing(resultsCollection, results);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Results to an array that doesn't contain it", () => {
          const results: IResults = { id: 123 };
          const resultsCollection: IResults[] = [{ id: 456 }];
          expectedResult = service.addResultsToCollectionIfMissing(resultsCollection, results);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(results);
        });

        it('should add only unique Results to an array', () => {
          const resultsArray: IResults[] = [{ id: 123 }, { id: 456 }, { id: 50607 }];
          const resultsCollection: IResults[] = [{ id: 123 }];
          expectedResult = service.addResultsToCollectionIfMissing(resultsCollection, ...resultsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const results: IResults = { id: 123 };
          const results2: IResults = { id: 456 };
          expectedResult = service.addResultsToCollectionIfMissing([], results, results2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(results);
          expect(expectedResult).toContain(results2);
        });

        it('should accept null and undefined values', () => {
          const results: IResults = { id: 123 };
          expectedResult = service.addResultsToCollectionIfMissing([], null, results, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(results);
        });

        it('should return initial array if no Results is added', () => {
          const resultsCollection: IResults[] = [{ id: 123 }];
          expectedResult = service.addResultsToCollectionIfMissing(resultsCollection, undefined, null);
          expect(expectedResult).toEqual(resultsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
