jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ResultateService } from '../service/resultate.service';
import { IResultate, Resultate } from '../resultate.model';
import { IPassen } from 'app/entities/passen/passen.model';
import { PassenService } from 'app/entities/passen/service/passen.service';
import { IGruppen } from 'app/entities/gruppen/gruppen.model';
import { GruppenService } from 'app/entities/gruppen/service/gruppen.service';
import { ISchuetze } from 'app/entities/schuetze/schuetze.model';
import { SchuetzeService } from 'app/entities/schuetze/service/schuetze.service';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { WettkampfService } from 'app/entities/wettkampf/service/wettkampf.service';

import { ResultateUpdateComponent } from './resultate-update.component';

describe('Component Tests', () => {
  describe('Resultate Management Update Component', () => {
    let comp: ResultateUpdateComponent;
    let fixture: ComponentFixture<ResultateUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let resultateService: ResultateService;
    let passenService: PassenService;
    let gruppenService: GruppenService;
    let schuetzeService: SchuetzeService;
    let wettkampfService: WettkampfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResultateUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ResultateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultateUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      resultateService = TestBed.inject(ResultateService);
      passenService = TestBed.inject(PassenService);
      gruppenService = TestBed.inject(GruppenService);
      schuetzeService = TestBed.inject(SchuetzeService);
      wettkampfService = TestBed.inject(WettkampfService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call passe1 query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const passe1: IPassen = { id: 1899 };
        resultate.passe1 = passe1;

        const passe1Collection: IPassen[] = [{ id: 97842 }];
        jest.spyOn(passenService, 'query').mockReturnValue(of(new HttpResponse({ body: passe1Collection })));
        const expectedCollection: IPassen[] = [passe1, ...passe1Collection];
        jest.spyOn(passenService, 'addPassenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(passenService.query).toHaveBeenCalled();
        expect(passenService.addPassenToCollectionIfMissing).toHaveBeenCalledWith(passe1Collection, passe1);
        expect(comp.passe1sCollection).toEqual(expectedCollection);
      });

      it('Should call passe2 query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const passe2: IPassen = { id: 81609 };
        resultate.passe2 = passe2;

        const passe2Collection: IPassen[] = [{ id: 64656 }];
        jest.spyOn(passenService, 'query').mockReturnValue(of(new HttpResponse({ body: passe2Collection })));
        const expectedCollection: IPassen[] = [passe2, ...passe2Collection];
        jest.spyOn(passenService, 'addPassenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(passenService.query).toHaveBeenCalled();
        expect(passenService.addPassenToCollectionIfMissing).toHaveBeenCalledWith(passe2Collection, passe2);
        expect(comp.passe2sCollection).toEqual(expectedCollection);
      });

      it('Should call passe3 query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const passe3: IPassen = { id: 84689 };
        resultate.passe3 = passe3;

        const passe3Collection: IPassen[] = [{ id: 46950 }];
        jest.spyOn(passenService, 'query').mockReturnValue(of(new HttpResponse({ body: passe3Collection })));
        const expectedCollection: IPassen[] = [passe3, ...passe3Collection];
        jest.spyOn(passenService, 'addPassenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(passenService.query).toHaveBeenCalled();
        expect(passenService.addPassenToCollectionIfMissing).toHaveBeenCalledWith(passe3Collection, passe3);
        expect(comp.passe3sCollection).toEqual(expectedCollection);
      });

      it('Should call passe4 query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const passe4: IPassen = { id: 11131 };
        resultate.passe4 = passe4;

        const passe4Collection: IPassen[] = [{ id: 12199 }];
        jest.spyOn(passenService, 'query').mockReturnValue(of(new HttpResponse({ body: passe4Collection })));
        const expectedCollection: IPassen[] = [passe4, ...passe4Collection];
        jest.spyOn(passenService, 'addPassenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(passenService.query).toHaveBeenCalled();
        expect(passenService.addPassenToCollectionIfMissing).toHaveBeenCalledWith(passe4Collection, passe4);
        expect(comp.passe4sCollection).toEqual(expectedCollection);
      });

      it('Should call gruppe query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const gruppe: IGruppen = { id: 24544 };
        resultate.gruppe = gruppe;

        const gruppeCollection: IGruppen[] = [{ id: 10336 }];
        jest.spyOn(gruppenService, 'query').mockReturnValue(of(new HttpResponse({ body: gruppeCollection })));
        const expectedCollection: IGruppen[] = [gruppe, ...gruppeCollection];
        jest.spyOn(gruppenService, 'addGruppenToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(gruppenService.query).toHaveBeenCalled();
        expect(gruppenService.addGruppenToCollectionIfMissing).toHaveBeenCalledWith(gruppeCollection, gruppe);
        expect(comp.gruppesCollection).toEqual(expectedCollection);
      });

      it('Should call Schuetze query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const schuetze: ISchuetze = { id: 34111 };
        resultate.schuetze = schuetze;

        const schuetzeCollection: ISchuetze[] = [{ id: 81036 }];
        jest.spyOn(schuetzeService, 'query').mockReturnValue(of(new HttpResponse({ body: schuetzeCollection })));
        const additionalSchuetzes = [schuetze];
        const expectedCollection: ISchuetze[] = [...additionalSchuetzes, ...schuetzeCollection];
        jest.spyOn(schuetzeService, 'addSchuetzeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(schuetzeService.query).toHaveBeenCalled();
        expect(schuetzeService.addSchuetzeToCollectionIfMissing).toHaveBeenCalledWith(schuetzeCollection, ...additionalSchuetzes);
        expect(comp.schuetzesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Wettkampf query and add missing value', () => {
        const resultate: IResultate = { id: 456 };
        const wettkampf: IWettkampf = { id: 36706 };
        resultate.wettkampf = wettkampf;

        const wettkampfCollection: IWettkampf[] = [{ id: 62836 }];
        jest.spyOn(wettkampfService, 'query').mockReturnValue(of(new HttpResponse({ body: wettkampfCollection })));
        const additionalWettkampfs = [wettkampf];
        const expectedCollection: IWettkampf[] = [...additionalWettkampfs, ...wettkampfCollection];
        jest.spyOn(wettkampfService, 'addWettkampfToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(wettkampfService.query).toHaveBeenCalled();
        expect(wettkampfService.addWettkampfToCollectionIfMissing).toHaveBeenCalledWith(wettkampfCollection, ...additionalWettkampfs);
        expect(comp.wettkampfsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const resultate: IResultate = { id: 456 };
        const passe1: IPassen = { id: 48052 };
        resultate.passe1 = passe1;
        const passe2: IPassen = { id: 59389 };
        resultate.passe2 = passe2;
        const passe3: IPassen = { id: 59362 };
        resultate.passe3 = passe3;
        const passe4: IPassen = { id: 92137 };
        resultate.passe4 = passe4;
        const gruppe: IGruppen = { id: 97148 };
        resultate.gruppe = gruppe;
        const schuetze: ISchuetze = { id: 53609 };
        resultate.schuetze = schuetze;
        const wettkampf: IWettkampf = { id: 47418 };
        resultate.wettkampf = wettkampf;

        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(resultate));
        expect(comp.passe1sCollection).toContain(passe1);
        expect(comp.passe2sCollection).toContain(passe2);
        expect(comp.passe3sCollection).toContain(passe3);
        expect(comp.passe4sCollection).toContain(passe4);
        expect(comp.gruppesCollection).toContain(gruppe);
        expect(comp.schuetzesSharedCollection).toContain(schuetze);
        expect(comp.wettkampfsSharedCollection).toContain(wettkampf);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Resultate>>();
        const resultate = { id: 123 };
        jest.spyOn(resultateService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: resultate }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(resultateService.update).toHaveBeenCalledWith(resultate);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Resultate>>();
        const resultate = new Resultate();
        jest.spyOn(resultateService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: resultate }));
        saveSubject.complete();

        // THEN
        expect(resultateService.create).toHaveBeenCalledWith(resultate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Resultate>>();
        const resultate = { id: 123 };
        jest.spyOn(resultateService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ resultate });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(resultateService.update).toHaveBeenCalledWith(resultate);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPassenById', () => {
        it('Should return tracked Passen primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPassenById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackGruppenById', () => {
        it('Should return tracked Gruppen primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackGruppenById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSchuetzeById', () => {
        it('Should return tracked Schuetze primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSchuetzeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackWettkampfById', () => {
        it('Should return tracked Wettkampf primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackWettkampfById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
