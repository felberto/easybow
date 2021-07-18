jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RangierungService } from '../service/rangierung.service';
import { IRangierung, Rangierung } from '../rangierung.model';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { WettkampfService } from 'app/entities/wettkampf/service/wettkampf.service';

import { RangierungUpdateComponent } from './rangierung-update.component';

describe('Component Tests', () => {
  describe('Rangierung Management Update Component', () => {
    let comp: RangierungUpdateComponent;
    let fixture: ComponentFixture<RangierungUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rangierungService: RangierungService;
    let wettkampfService: WettkampfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RangierungUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RangierungUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RangierungUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rangierungService = TestBed.inject(RangierungService);
      wettkampfService = TestBed.inject(WettkampfService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Wettkampf query and add missing value', () => {
        const rangierung: IRangierung = { id: 456 };
        const wettkampf: IWettkampf = { id: 32865 };
        rangierung.wettkampf = wettkampf;

        const wettkampfCollection: IWettkampf[] = [{ id: 59238 }];
        jest.spyOn(wettkampfService, 'query').mockReturnValue(of(new HttpResponse({ body: wettkampfCollection })));
        const additionalWettkampfs = [wettkampf];
        const expectedCollection: IWettkampf[] = [...additionalWettkampfs, ...wettkampfCollection];
        jest.spyOn(wettkampfService, 'addWettkampfToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rangierung });
        comp.ngOnInit();

        expect(wettkampfService.query).toHaveBeenCalled();
        expect(wettkampfService.addWettkampfToCollectionIfMissing).toHaveBeenCalledWith(wettkampfCollection, ...additionalWettkampfs);
        expect(comp.wettkampfsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const rangierung: IRangierung = { id: 456 };
        const wettkampf: IWettkampf = { id: 77751 };
        rangierung.wettkampf = wettkampf;

        activatedRoute.data = of({ rangierung });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(rangierung));
        expect(comp.wettkampfsSharedCollection).toContain(wettkampf);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rangierung>>();
        const rangierung = { id: 123 };
        jest.spyOn(rangierungService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rangierung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rangierung }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rangierungService.update).toHaveBeenCalledWith(rangierung);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rangierung>>();
        const rangierung = new Rangierung();
        jest.spyOn(rangierungService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rangierung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rangierung }));
        saveSubject.complete();

        // THEN
        expect(rangierungService.create).toHaveBeenCalledWith(rangierung);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rangierung>>();
        const rangierung = { id: 123 };
        jest.spyOn(rangierungService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rangierung });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rangierungService.update).toHaveBeenCalledWith(rangierung);
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
