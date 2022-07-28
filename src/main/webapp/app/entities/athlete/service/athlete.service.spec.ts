import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Position } from 'app/entities/enumerations/position.model';
import { Athlete, IAthlete } from '../athlete.model';

import { AthleteService } from './athlete.service';
import { Nationality } from '../../enumerations/nationality.model';
import { Gender } from '../../enumerations/gender.model';

describe('Service Tests', () => {
  describe('Athlete Service', () => {
    let service: AthleteService;
    let httpMock: HttpTestingController;
    let elemDefault: IAthlete;
    let expectedResult: IAthlete | IAthlete[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AthleteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        firstName: 'AAAAAAA',
        yearOfBirth: 0,
        nationality: Nationality.SUI,
        gender: Gender.MALE,
        position: Position.FREE,
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

      it('should create a Athlete', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Athlete()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Athlete', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBBB',
            firstName: 'BBBBBBB',
            yearOfBirth: 0,
            nationality: Nationality.SUI,
            gender: Gender.MALE,
            position: Position.FREE,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Athlete', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new Athlete()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Athlete', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBBB',
            firstName: 'BBBBBBB',
            yearOfBirth: 0,
            nationality: Nationality.SUI,
            gender: Gender.MALE,
            position: Position.FREE,
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

      it('should delete a Athlete', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAthleteToCollectionIfMissing', () => {
        it('should add a Athlete to an empty array', () => {
          const athlete: IAthlete = { id: 123 };
          expectedResult = service.addAthleteToCollectionIfMissing([], athlete);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(athlete);
        });

        it('should not add a Athlete to an array that contains it', () => {
          const athlete: IAthlete = { id: 123 };
          const athleteCollection: IAthlete[] = [
            {
              ...athlete,
            },
            { id: 456 },
          ];
          expectedResult = service.addAthleteToCollectionIfMissing(athleteCollection, athlete);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Athlete to an array that doesn't contain it", () => {
          const athlete: IAthlete = { id: 123 };
          const athleteCollection: IAthlete[] = [{ id: 456 }];
          expectedResult = service.addAthleteToCollectionIfMissing(athleteCollection, athlete);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(athlete);
        });

        it('should add only unique Athlete to an array', () => {
          const athleteArray: IAthlete[] = [{ id: 123 }, { id: 456 }, { id: 75695 }];
          const athleteCollection: IAthlete[] = [{ id: 123 }];
          expectedResult = service.addAthleteToCollectionIfMissing(athleteCollection, ...athleteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const athlete: IAthlete = { id: 123 };
          const athlete2: IAthlete = { id: 456 };
          expectedResult = service.addAthleteToCollectionIfMissing([], athlete, athlete2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(athlete);
          expect(expectedResult).toContain(athlete2);
        });

        it('should accept null and undefined values', () => {
          const athlete: IAthlete = { id: 123 };
          expectedResult = service.addAthleteToCollectionIfMissing([], null, athlete, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(athlete);
        });

        it('should return initial array if no Athlete is added', () => {
          const athleteCollection: IAthlete[] = [{ id: 123 }];
          expectedResult = service.addAthleteToCollectionIfMissing(athleteCollection, undefined, null);
          expect(expectedResult).toEqual(athleteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
