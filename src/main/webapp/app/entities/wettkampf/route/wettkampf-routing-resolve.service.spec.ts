jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IWettkampf, Wettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';

import { WettkampfRoutingResolveService } from './wettkampf-routing-resolve.service';

describe('Service Tests', () => {
  describe('Wettkampf routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: WettkampfRoutingResolveService;
    let service: WettkampfService;
    let resultWettkampf: IWettkampf | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(WettkampfRoutingResolveService);
      service = TestBed.inject(WettkampfService);
      resultWettkampf = undefined;
    });

    describe('resolve', () => {
      it('should return IWettkampf returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultWettkampf = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultWettkampf).toEqual({ id: 123 });
      });

      it('should return new IWettkampf if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultWettkampf = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultWettkampf).toEqual(new Wettkampf());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Wettkampf })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultWettkampf = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultWettkampf).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
