jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVerband, Verband } from '../verband.model';
import { VerbandService } from '../service/verband.service';

import { VerbandRoutingResolveService } from './verband-routing-resolve.service';

describe('Service Tests', () => {
  describe('Verband routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VerbandRoutingResolveService;
    let service: VerbandService;
    let resultVerband: IVerband | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VerbandRoutingResolveService);
      service = TestBed.inject(VerbandService);
      resultVerband = undefined;
    });

    describe('resolve', () => {
      it('should return IVerband returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVerband = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVerband).toEqual({ id: 123 });
      });

      it('should return new IVerband if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVerband = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVerband).toEqual(new Verband());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Verband })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVerband = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVerband).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
