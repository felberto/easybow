jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRangierung, Rangierung } from '../rangierung.model';
import { RangierungService } from '../service/rangierung.service';

import { RangierungRoutingResolveService } from './rangierung-routing-resolve.service';

describe('Service Tests', () => {
  describe('Rangierung routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RangierungRoutingResolveService;
    let service: RangierungService;
    let resultRangierung: IRangierung | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RangierungRoutingResolveService);
      service = TestBed.inject(RangierungService);
      resultRangierung = undefined;
    });

    describe('resolve', () => {
      it('should return IRangierung returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRangierung = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRangierung).toEqual({ id: 123 });
      });

      it('should return new IRangierung if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRangierung = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRangierung).toEqual(new Rangierung());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Rangierung })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRangierung = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRangierung).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
