import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRunde, Runde } from '../runde.model';
import { RundeService } from '../service/runde.service';

@Injectable({ providedIn: 'root' })
export class RundeRoutingResolveService implements Resolve<IRunde> {
  constructor(protected service: RundeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRunde> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((runde: HttpResponse<Runde>) => {
          if (runde.body) {
            return of(runde.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Runde());
  }
}
