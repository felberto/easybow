jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VereinService } from '../service/verein.service';
import { IVerein, Verein } from '../verein.model';
import { IVerband } from 'app/entities/verband/verband.model';
import { VerbandService } from 'app/entities/verband/service/verband.service';

import { VereinUpdateComponent } from './verein-update.component';

describe('Component Tests', () => {
  describe('Verein Management Update Component', () => {
    let comp: VereinUpdateComponent;
    let fixture: ComponentFixture<VereinUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let vereinService: VereinService;
    let verbandService: VerbandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VereinUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VereinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VereinUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      vereinService = TestBed.inject(VereinService);
      verbandService = TestBed.inject(VerbandService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call verband query and add missing value', () => {
        const verein: IVerein = { id: 456 };
        const verband: IVerband = { id: 83465 };
        verein.verband = verband;

        const verbandCollection: IVerband[] = [{ id: 11762 }];
        jest.spyOn(verbandService, 'query').mockReturnValue(of(new HttpResponse({ body: verbandCollection })));
        const expectedCollection: IVerband[] = [verband, ...verbandCollection];
        jest.spyOn(verbandService, 'addVerbandToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ verein });
        comp.ngOnInit();

        expect(verbandService.query).toHaveBeenCalled();
        expect(verbandService.addVerbandToCollectionIfMissing).toHaveBeenCalledWith(verbandCollection, verband);
        expect(comp.verbandsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const verein: IVerein = { id: 456 };
        const verband: IVerband = { id: 97478 };
        verein.verband = verband;

        activatedRoute.data = of({ verein });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(verein));
        expect(comp.verbandsCollection).toContain(verband);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Verein>>();
        const verein = { id: 123 };
        jest.spyOn(vereinService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ verein });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: verein }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(vereinService.update).toHaveBeenCalledWith(verein);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Verein>>();
        const verein = new Verein();
        jest.spyOn(vereinService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ verein });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: verein }));
        saveSubject.complete();

        // THEN
        expect(vereinService.create).toHaveBeenCalledWith(verein);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Verein>>();
        const verein = { id: 123 };
        jest.spyOn(vereinService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ verein });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(vereinService.update).toHaveBeenCalledWith(verein);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVerbandById', () => {
        it('Should return tracked Verband primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVerbandById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
