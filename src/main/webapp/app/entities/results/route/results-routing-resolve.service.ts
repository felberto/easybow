import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResults, Results } from '../results.model';
import { ResultsService } from '../service/results.service';

@Injectable({ providedIn: 'root' })
export class ResultsRoutingResolveService implements Resolve<IResults> {
  constructor(protected service: ResultsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResults> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((results: HttpResponse<Results>) => {
          if (results.body) {
            return of(results.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Results());
  }
}
