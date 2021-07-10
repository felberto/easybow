jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IResultate, Resultate } from '../resultate.model';
import { ResultateService } from '../service/resultate.service';

import { ResultateRoutingResolveService } from './resultate-routing-resolve.service';

describe('Service Tests', () => {
  describe('Resultate routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ResultateRoutingResolveService;
    let service: ResultateService;
    let resultResultate: IResultate | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ResultateRoutingResolveService);
      service = TestBed.inject(ResultateService);
      resultResultate = undefined;
    });

    describe('resolve', () => {
      it('should return IResultate returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResultate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultResultate).toEqual({ id: 123 });
      });

      it('should return new IResultate if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResultate = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultResultate).toEqual(new Resultate());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Resultate })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultResultate = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultResultate).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
