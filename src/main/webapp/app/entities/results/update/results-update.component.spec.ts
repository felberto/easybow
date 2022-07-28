jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ResultsService } from '../service/results.service';
import { IResults, Results } from '../results.model';
import { ISeries } from 'app/entities/series/series.model';
import { SeriesService } from 'app/entities/series/service/series.service';
import { IGroup } from 'app/entities/group/group.model';
import { GroupService } from 'app/entities/group/service/group.service';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { AthleteService } from 'app/entities/athlete/service/athlete.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from 'app/entities/competition/service/competition.service';

import { ResultsUpdateComponent } from './results-update.component';

describe('Component Tests', () => {
  describe('Results Management Update Component', () => {
    let comp: ResultsUpdateComponent;
    let fixture: ComponentFixture<ResultsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let resultsService: ResultsService;
    let seriesService: SeriesService;
    let groupService: GroupService;
    let athleteService: AthleteService;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResultsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ResultsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      resultsService = TestBed.inject(ResultsService);
      seriesService = TestBed.inject(SeriesService);
      groupService = TestBed.inject(GroupService);
      athleteService = TestBed.inject(AthleteService);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call serie1 query and add missing value', () => {
        const results: IResults = { id: 456 };
        const serie1: ISeries = { id: 1899 };
        results.serie1 = serie1;

        const serie1Collection: ISeries[] = [{ id: 97842 }];
        jest.spyOn(seriesService, 'query').mockReturnValue(of(new HttpResponse({ body: serie1Collection })));
        const expectedCollection: ISeries[] = [serie1, ...serie1Collection];
        jest.spyOn(seriesService, 'addSeriesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(seriesService.query).toHaveBeenCalled();
        expect(seriesService.addSeriesToCollectionIfMissing).toHaveBeenCalledWith(serie1Collection, serie1);
        expect(comp.serie1sCollection).toEqual(expectedCollection);
      });

      it('Should call serie2 query and add missing value', () => {
        const results: IResults = { id: 456 };
        const serie2: ISeries = { id: 81609 };
        results.serie2 = serie2;

        const serie2Collection: ISeries[] = [{ id: 64656 }];
        jest.spyOn(seriesService, 'query').mockReturnValue(of(new HttpResponse({ body: serie2Collection })));
        const expectedCollection: ISeries[] = [serie2, ...serie2Collection];
        jest.spyOn(seriesService, 'addSeriesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(seriesService.query).toHaveBeenCalled();
        expect(seriesService.addSeriesToCollectionIfMissing).toHaveBeenCalledWith(serie2Collection, serie2);
        expect(comp.serie2sCollection).toEqual(expectedCollection);
      });

      it('Should call serie3 query and add missing value', () => {
        const results: IResults = { id: 456 };
        const serie3: ISeries = { id: 84689 };
        results.serie3 = serie3;

        const serie3Collection: ISeries[] = [{ id: 46950 }];
        jest.spyOn(seriesService, 'query').mockReturnValue(of(new HttpResponse({ body: serie3Collection })));
        const expectedCollection: ISeries[] = [serie3, ...serie3Collection];
        jest.spyOn(seriesService, 'addSeriesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(seriesService.query).toHaveBeenCalled();
        expect(seriesService.addSeriesToCollectionIfMissing).toHaveBeenCalledWith(serie3Collection, serie3);
        expect(comp.serie3sCollection).toEqual(expectedCollection);
      });

      it('Should call serie4 query and add missing value', () => {
        const results: IResults = { id: 456 };
        const serie4: ISeries = { id: 11131 };
        results.serie4 = serie4;

        const serie4Collection: ISeries[] = [{ id: 12199 }];
        jest.spyOn(seriesService, 'query').mockReturnValue(of(new HttpResponse({ body: serie4Collection })));
        const expectedCollection: ISeries[] = [serie4, ...serie4Collection];
        jest.spyOn(seriesService, 'addSeriesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(seriesService.query).toHaveBeenCalled();
        expect(seriesService.addSeriesToCollectionIfMissing).toHaveBeenCalledWith(serie4Collection, serie4);
        expect(comp.serie4sCollection).toEqual(expectedCollection);
      });

      it('Should call Group query and add missing value', () => {
        const results: IResults = { id: 456 };
        const group: IGroup = { id: 24544 };
        results.group = group;

        const groupCollection: IGroup[] = [{ id: 10336 }];
        jest.spyOn(groupService, 'query').mockReturnValue(of(new HttpResponse({ body: groupCollection })));
        const additionalGroups = [group];
        const expectedCollection: IGroup[] = [...additionalGroups, ...groupCollection];
        jest.spyOn(groupService, 'addGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(groupService.query).toHaveBeenCalled();
        expect(groupService.addGroupToCollectionIfMissing).toHaveBeenCalledWith(groupCollection, ...additionalGroups);
        expect(comp.groupsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Athlete query and add missing value', () => {
        const results: IResults = { id: 456 };
        const athlete: IAthlete = { id: 34111 };
        results.athlete = athlete;

        const athleteCollection: IAthlete[] = [{ id: 81036 }];
        jest.spyOn(athleteService, 'query').mockReturnValue(of(new HttpResponse({ body: athleteCollection })));
        const additionalAthletes = [athlete];
        const expectedCollection: IAthlete[] = [...additionalAthletes, ...athleteCollection];
        jest.spyOn(athleteService, 'addAthleteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(athleteService.query).toHaveBeenCalled();
        expect(athleteService.addAthleteToCollectionIfMissing).toHaveBeenCalledWith(athleteCollection, ...additionalAthletes);
        expect(comp.athletesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Competition query and add missing value', () => {
        const results: IResults = { id: 456 };
        const competition: ICompetition = { id: 36706 };
        results.competition = competition;

        const competitionCollection: ICompetition[] = [{ id: 62836 }];
        jest.spyOn(competitionService, 'query').mockReturnValue(of(new HttpResponse({ body: competitionCollection })));
        const additionalCompetitions = [competition];
        const expectedCollection: ICompetition[] = [...additionalCompetitions, ...competitionCollection];
        jest.spyOn(competitionService, 'addCompetitionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(competitionService.query).toHaveBeenCalled();
        expect(competitionService.addCompetitionToCollectionIfMissing).toHaveBeenCalledWith(
          competitionCollection,
          ...additionalCompetitions
        );
        expect(comp.competitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const results: IResults = { id: 456 };
        const serie1: ISeries = { id: 48052 };
        results.serie1 = serie1;
        const serie2: ISeries = { id: 59389 };
        results.serie2 = serie2;
        const serie3: ISeries = { id: 59362 };
        results.serie3 = serie3;
        const serie4: ISeries = { id: 92137 };
        results.serie4 = serie4;
        const group: IGroup = { id: 97148 };
        results.group = group;
        const athlete: IAthlete = { id: 53609 };
        results.athlete = athlete;
        const competition: ICompetition = { id: 47418 };
        results.competition = competition;

        activatedRoute.data = of({ results });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(results));
        expect(comp.serie1sCollection).toContain(serie1);
        expect(comp.serie2sCollection).toContain(serie2);
        expect(comp.serie3sCollection).toContain(serie3);
        expect(comp.serie4sCollection).toContain(serie4);
        expect(comp.groupsSharedCollection).toContain(group);
        expect(comp.athletesSharedCollection).toContain(athlete);
        expect(comp.competitionsSharedCollection).toContain(competition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Results>>();
        const results = { id: 123 };
        jest.spyOn(resultsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ results });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: results }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(resultsService.update).toHaveBeenCalledWith(results);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Results>>();
        const results = new Results();
        jest.spyOn(resultsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ results });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: results }));
        saveSubject.complete();

        // THEN
        expect(resultsService.create).toHaveBeenCalledWith(results);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Results>>();
        const results = { id: 123 };
        jest.spyOn(resultsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ results });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(resultsService.update).toHaveBeenCalledWith(results);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackSeriesById', () => {
        it('Should return tracked Series primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSeriesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackGroupById', () => {
        it('Should return tracked Group primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGroupById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAthleteById', () => {
        it('Should return tracked Athlete primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAthleteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCompetitionById', () => {
        it('Should return tracked Competition primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCompetitionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
