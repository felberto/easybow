jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClubService } from '../service/club.service';
import { Club, IClub } from '../club.model';
import { IAssociation } from 'app/entities/association/association.model';
import { AssociationService } from 'app/entities/association/service/association.service';

import { ClubUpdateComponent } from './club-update.component';

describe('Component Tests', () => {
  describe('Club Management Update Component', () => {
    let comp: ClubUpdateComponent;
    let fixture: ComponentFixture<ClubUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let clubService: ClubService;
    let associationService: AssociationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClubUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClubUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClubUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      clubService = TestBed.inject(ClubService);
      associationService = TestBed.inject(AssociationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Association query and add missing value', () => {
        const club: IClub = { id: 456 };
        const association: IAssociation = { id: 83465 };
        club.association = association;

        const associationCollection: IAssociation[] = [{ id: 11762 }];
        jest.spyOn(associationService, 'query').mockReturnValue(of(new HttpResponse({ body: associationCollection })));
        const additionalAssociations = [association];
        const expectedCollection: IAssociation[] = [...additionalAssociations, ...associationCollection];
        jest.spyOn(associationService, 'addAssociationToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ club });
        comp.ngOnInit();

        expect(associationService.query).toHaveBeenCalled();
        expect(associationService.addAssociationToCollectionIfMissing).toHaveBeenCalledWith(
          associationCollection,
          ...additionalAssociations
        );
        expect(comp.associationsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const club: IClub = { id: 456 };
        const association: IAssociation = { id: 97478 };
        club.association = association;

        activatedRoute.data = of({ club });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(club));
        expect(comp.associationsSharedCollection).toContain(association);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Club>>();
        const club = { id: 123 };
        jest.spyOn(clubService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ club });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: club }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(clubService.update).toHaveBeenCalledWith(club);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Club>>();
        const club = new Club();
        jest.spyOn(clubService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ club });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: club }));
        saveSubject.complete();

        // THEN
        expect(clubService.create).toHaveBeenCalledWith(club);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Club>>();
        const club = { id: 123 };
        jest.spyOn(clubService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ club });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(clubService.update).toHaveBeenCalledWith(club);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAssociationById', () => {
        it('Should return tracked Association primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAssociationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
