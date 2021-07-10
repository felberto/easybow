import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVerband, Verband } from '../verband.model';

import { VerbandService } from './verband.service';

describe('Service Tests', () => {
  describe('Verband Service', () => {
    let service: VerbandService;
    let httpMock: HttpTestingController;
    let elemDefault: IVerband;
    let expectedResult: IVerband | IVerband[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VerbandService);
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

      it('should create a Verband', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Verband()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Verband', () => {
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

      it('should partial update a Verband', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Verband()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Verband', () => {
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

      it('should delete a Verband', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVerbandToCollectionIfMissing', () => {
        it('should add a Verband to an empty array', () => {
          const verband: IVerband = { id: 123 };
          expectedResult = service.addVerbandToCollectionIfMissing([], verband);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(verband);
        });

        it('should not add a Verband to an array that contains it', () => {
          const verband: IVerband = { id: 123 };
          const verbandCollection: IVerband[] = [
            {
              ...verband,
            },
            { id: 456 },
          ];
          expectedResult = service.addVerbandToCollectionIfMissing(verbandCollection, verband);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Verband to an array that doesn't contain it", () => {
          const verband: IVerband = { id: 123 };
          const verbandCollection: IVerband[] = [{ id: 456 }];
          expectedResult = service.addVerbandToCollectionIfMissing(verbandCollection, verband);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(verband);
        });

        it('should add only unique Verband to an array', () => {
          const verbandArray: IVerband[] = [{ id: 123 }, { id: 456 }, { id: 99942 }];
          const verbandCollection: IVerband[] = [{ id: 123 }];
          expectedResult = service.addVerbandToCollectionIfMissing(verbandCollection, ...verbandArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const verband: IVerband = { id: 123 };
          const verband2: IVerband = { id: 456 };
          expectedResult = service.addVerbandToCollectionIfMissing([], verband, verband2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(verband);
          expect(expectedResult).toContain(verband2);
        });

        it('should accept null and undefined values', () => {
          const verband: IVerband = { id: 123 };
          expectedResult = service.addVerbandToCollectionIfMissing([], null, verband, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(verband);
        });

        it('should return initial array if no Verband is added', () => {
          const verbandCollection: IVerband[] = [{ id: 123 }];
          expectedResult = service.addVerbandToCollectionIfMissing(verbandCollection, undefined, null);
          expect(expectedResult).toEqual(verbandCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
