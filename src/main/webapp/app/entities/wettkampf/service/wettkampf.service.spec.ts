import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWettkampf, Wettkampf } from '../wettkampf.model';

import { WettkampfService } from './wettkampf.service';

describe('Service Tests', () => {
  describe('Wettkampf Service', () => {
    let service: WettkampfService;
    let httpMock: HttpTestingController;
    let elemDefault: IWettkampf;
    let expectedResult: IWettkampf | IWettkampf[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(WettkampfService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        jahr: 0,
        anzahlRunden: 0,
        anzahlPassen: 0,
        finalRunde: false,
        finalVorbereitung: false,
        anzahlFinalteilnehmer: 0,
        anzahlPassenFinal: 0,
        anzahlTeam: 0,
        template: false,
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

      it('should create a Wettkampf', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Wettkampf()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Wettkampf', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            jahr: 1,
            anzahlRunden: 1,
            anzahlPassen: 1,
            finalRunde: true,
            finalVorbereitung: true,
            anzahlFinalteilnehmer: 1,
            anzahlPassenFinal: 1,
            anzahlTeam: 1,
            template: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Wettkampf', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            anzahlPassen: 1,
            finalVorbereitung: true,
            anzahlPassenFinal: 1,
          },
          new Wettkampf()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Wettkampf', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            jahr: 1,
            anzahlRunden: 1,
            anzahlPassen: 1,
            finalRunde: true,
            finalVorbereitung: true,
            anzahlFinalteilnehmer: 1,
            anzahlPassenFinal: 1,
            anzahlTeam: 1,
            template: true,
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

      it('should delete a Wettkampf', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addWettkampfToCollectionIfMissing', () => {
        it('should add a Wettkampf to an empty array', () => {
          const wettkampf: IWettkampf = { id: 123 };
          expectedResult = service.addWettkampfToCollectionIfMissing([], wettkampf);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(wettkampf);
        });

        it('should not add a Wettkampf to an array that contains it', () => {
          const wettkampf: IWettkampf = { id: 123 };
          const wettkampfCollection: IWettkampf[] = [
            {
              ...wettkampf,
            },
            { id: 456 },
          ];
          expectedResult = service.addWettkampfToCollectionIfMissing(wettkampfCollection, wettkampf);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Wettkampf to an array that doesn't contain it", () => {
          const wettkampf: IWettkampf = { id: 123 };
          const wettkampfCollection: IWettkampf[] = [{ id: 456 }];
          expectedResult = service.addWettkampfToCollectionIfMissing(wettkampfCollection, wettkampf);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(wettkampf);
        });

        it('should add only unique Wettkampf to an array', () => {
          const wettkampfArray: IWettkampf[] = [{ id: 123 }, { id: 456 }, { id: 14443 }];
          const wettkampfCollection: IWettkampf[] = [{ id: 123 }];
          expectedResult = service.addWettkampfToCollectionIfMissing(wettkampfCollection, ...wettkampfArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const wettkampf: IWettkampf = { id: 123 };
          const wettkampf2: IWettkampf = { id: 456 };
          expectedResult = service.addWettkampfToCollectionIfMissing([], wettkampf, wettkampf2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(wettkampf);
          expect(expectedResult).toContain(wettkampf2);
        });

        it('should accept null and undefined values', () => {
          const wettkampf: IWettkampf = { id: 123 };
          expectedResult = service.addWettkampfToCollectionIfMissing([], null, wettkampf, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(wettkampf);
        });

        it('should return initial array if no Wettkampf is added', () => {
          const wettkampfCollection: IWettkampf[] = [{ id: 123 }];
          expectedResult = service.addWettkampfToCollectionIfMissing(wettkampfCollection, undefined, null);
          expect(expectedResult).toEqual(wettkampfCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
