import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISeries, Series } from '../series.model';

import { SeriesService } from './series.service';

describe('Service Tests', () => {
  describe('Series Service', () => {
    let service: SeriesService;
    let httpMock: HttpTestingController;
    let elemDefault: ISeries;
    let expectedResult: ISeries | ISeries[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SeriesService);
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

      it('should create a Series', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Series()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Series', () => {
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

      it('should partial update a Series', () => {
        const patchObject = Object.assign(
          {
            p2: 1,
            p3: 1,
            p4: 1,
            p8: 1,
            p10: 1,
          },
          new Series()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Series', () => {
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

      it('should delete a Series', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSeriesToCollectionIfMissing', () => {
        it('should add a Series to an empty array', () => {
          const series: ISeries = { id: 123 };
          expectedResult = service.addSeriesToCollectionIfMissing([], series);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(series);
        });

        it('should not add a Series to an array that contains it', () => {
          const series: ISeries = { id: 123 };
          const seriesCollection: ISeries[] = [
            {
              ...series,
            },
            { id: 456 },
          ];
          expectedResult = service.addSeriesToCollectionIfMissing(seriesCollection, series);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Series to an array that doesn't contain it", () => {
          const series: ISeries = { id: 123 };
          const seriesCollection: ISeries[] = [{ id: 456 }];
          expectedResult = service.addSeriesToCollectionIfMissing(seriesCollection, series);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(series);
        });

        it('should add only unique Series to an array', () => {
          const seriesArray: ISeries[] = [{ id: 123 }, { id: 456 }, { id: 57867 }];
          const seriesCollection: ISeries[] = [{ id: 123 }];
          expectedResult = service.addSeriesToCollectionIfMissing(seriesCollection, ...seriesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const series: ISeries = { id: 123 };
          const series2: ISeries = { id: 456 };
          expectedResult = service.addSeriesToCollectionIfMissing([], series, series2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(series);
          expect(expectedResult).toContain(series2);
        });

        it('should accept null and undefined values', () => {
          const series: ISeries = { id: 123 };
          expectedResult = service.addSeriesToCollectionIfMissing([], null, series, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(series);
        });

        it('should return initial array if no Series is added', () => {
          const seriesCollection: ISeries[] = [{ id: 123 }];
          expectedResult = service.addSeriesToCollectionIfMissing(seriesCollection, undefined, null);
          expect(expectedResult).toEqual(seriesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
