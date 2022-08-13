jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AssociationService } from '../service/association.service';
import { Association, IAssociation } from '../association.model';

import { AssociationUpdateComponent } from './association-update.component';

describe('Component Tests', () => {
  describe('Association Management Update Component', () => {
    let comp: AssociationUpdateComponent;
    let fixture: ComponentFixture<AssociationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let associationService: AssociationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AssociationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AssociationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssociationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      associationService = TestBed.inject(AssociationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const association: IAssociation = { id: 456 };

        activatedRoute.data = of({ association });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(association));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Association>>();
        const association = { id: 123 };
        jest.spyOn(associationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ association });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: association }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(associationService.update).toHaveBeenCalledWith(association);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Association>>();
        const association = new Association();
        jest.spyOn(associationService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ association });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: association }));
        saveSubject.complete();

        // THEN
        expect(associationService.create).toHaveBeenCalledWith(association);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Association>>();
        const association = { id: 123 };
        jest.spyOn(associationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ association });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(associationService.update).toHaveBeenCalledWith(association);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
