jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CompetitionService } from '../service/competition.service';
import { Competition, ICompetition } from '../competition.model';

import { CompetitionUpdateComponent } from './competition-update.component';

describe('Component Tests', () => {
  describe('Competition Management Update Component', () => {
    let comp: CompetitionUpdateComponent;
    let fixture: ComponentFixture<CompetitionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let competitionService: CompetitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CompetitionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CompetitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      competitionService = TestBed.inject(CompetitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const competition: ICompetition = { id: 456 };

        activatedRoute.data = of({ competition });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(competition));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Competition>>();
        const competition = { id: 123 };
        jest.spyOn(competitionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ competition });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: competition }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(competitionService.update).toHaveBeenCalledWith(competition);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Competition>>();
        const competition = new Competition();
        jest.spyOn(competitionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ competition });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: competition }));
        saveSubject.complete();

        // THEN
        expect(competitionService.create).toHaveBeenCalledWith(competition);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Competition>>();
        const competition = { id: 123 };
        jest.spyOn(competitionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ competition });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(competitionService.update).toHaveBeenCalledWith(competition);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
