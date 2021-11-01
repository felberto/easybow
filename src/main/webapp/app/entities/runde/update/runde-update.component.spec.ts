jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RundeService } from '../service/runde.service';
import { IRunde, Runde } from '../runde.model';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { WettkampfService } from 'app/entities/wettkampf/service/wettkampf.service';

import { RundeUpdateComponent } from './runde-update.component';

describe('Component Tests', () => {
  describe('Runde Management Update Component', () => {
    let comp: RundeUpdateComponent;
    let fixture: ComponentFixture<RundeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rundeService: RundeService;
    let wettkampfService: WettkampfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RundeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RundeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RundeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rundeService = TestBed.inject(RundeService);
      wettkampfService = TestBed.inject(WettkampfService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Wettkampf query and add missing value', () => {
        const runde: IRunde = { id: 456 };
        const wettkampf: IWettkampf = { id: 31888 };
        runde.wettkampf = wettkampf;

        const wettkampfCollection: IWettkampf[] = [{ id: 81360 }];
        spyOn(wettkampfService, 'query').and.returnValue(of(new HttpResponse({ body: wettkampfCollection })));
        const additionalWettkampfs = [wettkampf];
        const expectedCollection: IWettkampf[] = [...additionalWettkampfs, ...wettkampfCollection];
        spyOn(wettkampfService, 'addWettkampfToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ runde });
        comp.ngOnInit();

        expect(wettkampfService.query).toHaveBeenCalled();
        expect(wettkampfService.addWettkampfToCollectionIfMissing).toHaveBeenCalledWith(wettkampfCollection, ...additionalWettkampfs);
        expect(comp.wettkampfsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const runde: IRunde = { id: 456 };
        const wettkampf: IWettkampf = { id: 39719 };
        runde.wettkampf = wettkampf;

        activatedRoute.data = of({ runde });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(runde));
        expect(comp.wettkampfsSharedCollection).toContain(wettkampf);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const runde = { id: 123 };
        spyOn(rundeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ runde });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: runde }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rundeService.update).toHaveBeenCalledWith(runde);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const runde = new Runde();
        spyOn(rundeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ runde });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: runde }));
        saveSubject.complete();

        // THEN
        expect(rundeService.create).toHaveBeenCalledWith(runde);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const runde = { id: 123 };
        spyOn(rundeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ runde });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rundeService.update).toHaveBeenCalledWith(runde);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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