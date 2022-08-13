jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { Association, IAssociation } from '../association.model';
import { AssociationService } from '../service/association.service';

import { AssociationRoutingResolveService } from './association-routing-resolve.service';

describe('Service Tests', () => {
  describe('Association routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AssociationRoutingResolveService;
    let service: AssociationService;
    let resultAssociation: IAssociation | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AssociationRoutingResolveService);
      service = TestBed.inject(AssociationService);
      resultAssociation = undefined;
    });

    describe('resolve', () => {
      it('should return IAssociation returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAssociation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAssociation).toEqual({ id: 123 });
      });

      it('should return new IAssociation if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAssociation = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAssociation).toEqual(new Association());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Association })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAssociation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultAssociation).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
