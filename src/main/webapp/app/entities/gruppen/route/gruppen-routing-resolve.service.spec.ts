jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IGruppen, Gruppen } from '../gruppen.model';
import { GruppenService } from '../service/gruppen.service';

import { GruppenRoutingResolveService } from './gruppen-routing-resolve.service';

describe('Service Tests', () => {
  describe('Gruppen routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: GruppenRoutingResolveService;
    let service: GruppenService;
    let resultGruppen: IGruppen | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(GruppenRoutingResolveService);
      service = TestBed.inject(GruppenService);
      resultGruppen = undefined;
    });

    describe('resolve', () => {
      it('should return IGruppen returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGruppen = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGruppen).toEqual({ id: 123 });
      });

      it('should return new IGruppen if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGruppen = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultGruppen).toEqual(new Gruppen());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Gruppen })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultGruppen = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultGruppen).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
