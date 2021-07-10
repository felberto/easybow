jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VerbandService } from '../service/verband.service';
import { IVerband, Verband } from '../verband.model';

import { VerbandUpdateComponent } from './verband-update.component';

describe('Component Tests', () => {
  describe('Verband Management Update Component', () => {
    let comp: VerbandUpdateComponent;
    let fixture: ComponentFixture<VerbandUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let verbandService: VerbandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VerbandUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VerbandUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VerbandUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      verbandService = TestBed.inject(VerbandService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const verband: IVerband = { id: 456 };

        activatedRoute.data = of({ verband });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(verband));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Verband>>();
        const verband = { id: 123 };
        jest.spyOn(verbandService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ verband });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: verband }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(verbandService.update).toHaveBeenCalledWith(verband);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Verband>>();
        const verband = new Verband();
        jest.spyOn(verbandService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ verband });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: verband }));
        saveSubject.complete();

        // THEN
        expect(verbandService.create).toHaveBeenCalledWith(verband);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Verband>>();
        const verband = { id: 123 };
        jest.spyOn(verbandService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ verband });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(verbandService.update).toHaveBeenCalledWith(verband);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
