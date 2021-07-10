jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GruppenService } from '../service/gruppen.service';
import { IGruppen, Gruppen } from '../gruppen.model';

import { GruppenUpdateComponent } from './gruppen-update.component';

describe('Component Tests', () => {
  describe('Gruppen Management Update Component', () => {
    let comp: GruppenUpdateComponent;
    let fixture: ComponentFixture<GruppenUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let gruppenService: GruppenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GruppenUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GruppenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GruppenUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      gruppenService = TestBed.inject(GruppenService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const gruppen: IGruppen = { id: 456 };

        activatedRoute.data = of({ gruppen });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(gruppen));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Gruppen>>();
        const gruppen = { id: 123 };
        jest.spyOn(gruppenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ gruppen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: gruppen }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(gruppenService.update).toHaveBeenCalledWith(gruppen);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Gruppen>>();
        const gruppen = new Gruppen();
        jest.spyOn(gruppenService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ gruppen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: gruppen }));
        saveSubject.complete();

        // THEN
        expect(gruppenService.create).toHaveBeenCalledWith(gruppen);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Gruppen>>();
        const gruppen = { id: 123 };
        jest.spyOn(gruppenService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ gruppen });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(gruppenService.update).toHaveBeenCalledWith(gruppen);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
