jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PassenService } from '../service/passen.service';
import { IPassen, Passen } from '../passen.model';

import { PassenUpdateComponent } from './passen-update.component';

describe('Component Tests', () => {
  describe('Passen Management Update Component', () => {
    let comp: PassenUpdateComponent;
    let fixture: ComponentFixture<PassenUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let passenService: PassenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PassenUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PassenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PassenUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      passenService = TestBed.inject(PassenService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const passen: IPassen = { id: 456 };

        activatedRoute.data = of({ passen });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(passen));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Passen>>();
        const passen = { id: 123 };
        jest.spyOn(passenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ passen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: passen }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(passenService.update).toHaveBeenCalledWith(passen);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Passen>>();
        const passen = new Passen();
        jest.spyOn(passenService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ passen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: passen }));
        saveSubject.complete();

        // THEN
        expect(passenService.create).toHaveBeenCalledWith(passen);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Passen>>();
        const passen = { id: 123 };
        jest.spyOn(passenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ passen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(passenService.update).toHaveBeenCalledWith(passen);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
