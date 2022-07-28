jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IResults, Results } from '../results.model';
import { ResultsService } from '../service/results.service';

import { ResultsRoutingResolveService } from './results-routing-resolve.service';

describe('Service Tests', () => {
  describe('Results routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ResultsRoutingResolveService;
    let service: ResultsService;
    let resultResults: IResults | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ResultsRoutingResolveService);
      service = TestBed.inject(ResultsService);
      resultResults = undefined;
    });

    describe('resolve', () => {
      it('should return IResults returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResults = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultResults).toEqual({ id: 123 });
      });

      it('should return new IResults if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResults = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultResults).toEqual(new Results());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Results })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResults = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultResults).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
