jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AthleteService } from '../service/athlete.service';
import { Athlete, IAthlete } from '../athlete.model';
import { IClub } from 'app/entities/club/club.model';
import { ClubService } from 'app/entities/club/service/club.service';

import { AthleteUpdateComponent } from './athlete-update.component';

describe('Component Tests', () => {
  describe('Athlete Management Update Component', () => {
    let comp: AthleteUpdateComponent;
    let fixture: ComponentFixture<AthleteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let athleteService: AthleteService;
    let clubService: ClubService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AthleteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AthleteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      athleteService = TestBed.inject(AthleteService);
      clubService = TestBed.inject(ClubService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Club query and add missing value', () => {
        const athlete: IAthlete = { id: 456 };
        const club: IClub = { id: 3966 };
        athlete.club = club;

        const clubCollection: IClub[] = [{ id: 3402 }];
        jest.spyOn(clubService, 'query').mockReturnValue(of(new HttpResponse({ body: clubCollection })));
        const additionalClubs = [club];
        const expectedCollection: IClub[] = [...additionalClubs, ...clubCollection];
        jest.spyOn(clubService, 'addClubToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        expect(clubService.query).toHaveBeenCalled();
        expect(clubService.addClubToCollectionIfMissing).toHaveBeenCalledWith(clubCollection, ...additionalClubs);
        expect(comp.clubsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const athlete: IAthlete = { id: 456 };
        const club: IClub = { id: 55674 };
        athlete.club = club;

        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(athlete));
        expect(comp.clubsSharedCollection).toContain(club);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Athlete>>();
        const athlete = { id: 123 };
        jest.spyOn(athleteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: athlete }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(athleteService.update).toHaveBeenCalledWith(athlete);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Athlete>>();
        const athlete = new Athlete();
        jest.spyOn(athleteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: athlete }));
        saveSubject.complete();

        // THEN
        expect(athleteService.create).toHaveBeenCalledWith(athlete);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Athlete>>();
        const athlete = { id: 123 };
        jest.spyOn(athleteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ athlete });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(athleteService.update).toHaveBeenCalledWith(athlete);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackClubById', () => {
        it('Should return tracked Club primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackClubById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
