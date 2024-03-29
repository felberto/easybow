jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { Competition, ICompetition } from '../competition.model';
import { CompetitionService } from '../service/competition.service';

import { CompetitionRoutingResolveService } from './competition-routing-resolve.service';

describe('Service Tests', () => {
  describe('Competition routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CompetitionRoutingResolveService;
    let service: CompetitionService;
    let resultCompetition: ICompetition | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CompetitionRoutingResolveService);
      service = TestBed.inject(CompetitionService);
      resultCompetition = undefined;
    });

    describe('resolve', () => {
      it('should return ICompetition returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompetition = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCompetition).toEqual({ id: 123 });
      });

      it('should return new ICompetition if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompetition = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCompetition).toEqual(new Competition());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Competition })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCompetition = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCompetition).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
