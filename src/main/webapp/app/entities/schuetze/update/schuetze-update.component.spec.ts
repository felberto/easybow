jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SchuetzeService } from '../service/schuetze.service';
import { ISchuetze, Schuetze } from '../schuetze.model';
import { IVerein } from 'app/entities/verein/verein.model';
import { VereinService } from 'app/entities/verein/service/verein.service';

import { SchuetzeUpdateComponent } from './schuetze-update.component';

describe('Component Tests', () => {
  describe('Schuetze Management Update Component', () => {
    let comp: SchuetzeUpdateComponent;
    let fixture: ComponentFixture<SchuetzeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let schuetzeService: SchuetzeService;
    let vereinService: VereinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SchuetzeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SchuetzeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchuetzeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      schuetzeService = TestBed.inject(SchuetzeService);
      vereinService = TestBed.inject(VereinService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call verein query and add missing value', () => {
        const schuetze: ISchuetze = { id: 456 };
        const verein: IVerein = { id: 3966 };
        schuetze.verein = verein;

        const vereinCollection: IVerein[] = [{ id: 3402 }];
        jest.spyOn(vereinService, 'query').mockReturnValue(of(new HttpResponse({ body: vereinCollection })));
        const expectedCollection: IVerein[] = [verein, ...vereinCollection];
        jest.spyOn(vereinService, 'addVereinToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ schuetze });
        comp.ngOnInit();

        expect(vereinService.query).toHaveBeenCalled();
        expect(vereinService.addVereinToCollectionIfMissing).toHaveBeenCalledWith(vereinCollection, verein);
        expect(comp.vereinsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const schuetze: ISchuetze = { id: 456 };
        const verein: IVerein = { id: 55674 };
        schuetze.verein = verein;

        activatedRoute.data = of({ schuetze });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(schuetze));
        expect(comp.vereinsCollection).toContain(verein);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Schuetze>>();
        const schuetze = { id: 123 };
        jest.spyOn(schuetzeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ schuetze });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: schuetze }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(schuetzeService.update).toHaveBeenCalledWith(schuetze);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Schuetze>>();
        const schuetze = new Schuetze();
        jest.spyOn(schuetzeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ schuetze });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: schuetze }));
        saveSubject.complete();

        // THEN
        expect(schuetzeService.create).toHaveBeenCalledWith(schuetze);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Schuetze>>();
        const schuetze = { id: 123 };
        jest.spyOn(schuetzeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ schuetze });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(schuetzeService.update).toHaveBeenCalledWith(schuetze);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVereinById', () => {
        it('Should return tracked Verein primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVereinById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
