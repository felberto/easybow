import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Competition, ICompetition } from '../competition.model';

import { CompetitionService } from './competition.service';

describe('Service Tests', () => {
  describe('Competition Service', () => {
    let service: CompetitionService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompetition;
    let expectedResult: ICompetition | ICompetition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CompetitionService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        year: 0,
        numberOfRounds: 0,
        numberOfSeries: 0,
        finalRound: false,
        finalPreparation: false,
        numberOfFinalAthletes: 0,
        numberOfFinalSeries: 0,
        teamSize: 0,
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

      it('should create a Competition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Competition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Competition', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            jahr: 1,
            anzahlRunden: 1,
            anzahlSeries: 1,
            finalRunde: true,
            finalVorbereitung: true,
            anzahlFinalteilnehmer: 1,
            anzahlSeriesFinal: 1,
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

      it('should partial update a Competition', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            anzahlSeries: 1,
            finalVorbereitung: true,
            anzahlSeriesFinal: 1,
          },
          new Competition()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Competition', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            jahr: 1,
            anzahlRunden: 1,
            anzahlSeries: 1,
            finalRunde: true,
            finalVorbereitung: true,
            anzahlFinalteilnehmer: 1,
            anzahlSeriesFinal: 1,
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

      it('should delete a Competition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCompetitionToCollectionIfMissing', () => {
        it('should add a Competition to an empty array', () => {
          const competition: ICompetition = { id: 123 };
          expectedResult = service.addCompetitionToCollectionIfMissing([], competition);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(competition);
        });

        it('should not add a Competition to an array that contains it', () => {
          const competition: ICompetition = { id: 123 };
          const competitionCollection: ICompetition[] = [
            {
              ...competition,
            },
            { id: 456 },
          ];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, competition);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Competition to an array that doesn't contain it", () => {
          const competition: ICompetition = { id: 123 };
          const competitionCollection: ICompetition[] = [{ id: 456 }];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, competition);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(competition);
        });

        it('should add only unique Competition to an array', () => {
          const competitionArray: ICompetition[] = [{ id: 123 }, { id: 456 }, { id: 14443 }];
          const competitionCollection: ICompetition[] = [{ id: 123 }];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, ...competitionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const competition: ICompetition = { id: 123 };
          const competition2: ICompetition = { id: 456 };
          expectedResult = service.addCompetitionToCollectionIfMissing([], competition, competition2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(competition);
          expect(expectedResult).toContain(competition2);
        });

        it('should accept null and undefined values', () => {
          const competition: ICompetition = { id: 123 };
          expectedResult = service.addCompetitionToCollectionIfMissing([], null, competition, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(competition);
        });

        it('should return initial array if no Competition is added', () => {
          const competitionCollection: ICompetition[] = [{ id: 123 }];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, undefined, null);
          expect(expectedResult).toEqual(competitionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
