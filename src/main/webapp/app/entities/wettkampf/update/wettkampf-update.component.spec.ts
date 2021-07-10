jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { WettkampfService } from '../service/wettkampf.service';
import { IWettkampf, Wettkampf } from '../wettkampf.model';

import { WettkampfUpdateComponent } from './wettkampf-update.component';

describe('Component Tests', () => {
  describe('Wettkampf Management Update Component', () => {
    let comp: WettkampfUpdateComponent;
    let fixture: ComponentFixture<WettkampfUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let wettkampfService: WettkampfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WettkampfUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(WettkampfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WettkampfUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      wettkampfService = TestBed.inject(WettkampfService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const wettkampf: IWettkampf = { id: 456 };

        activatedRoute.data = of({ wettkampf });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(wettkampf));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Wettkampf>>();
        const wettkampf = { id: 123 };
        jest.spyOn(wettkampfService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ wettkampf });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: wettkampf }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(wettkampfService.update).toHaveBeenCalledWith(wettkampf);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Wettkampf>>();
        const wettkampf = new Wettkampf();
        jest.spyOn(wettkampfService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ wettkampf });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: wettkampf }));
        saveSubject.complete();

        // THEN
        expect(wettkampfService.create).toHaveBeenCalledWith(wettkampf);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Wettkampf>>();
        const wettkampf = { id: 123 };
        jest.spyOn(wettkampfService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ wettkampf });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(wettkampfService.update).toHaveBeenCalledWith(wettkampf);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
