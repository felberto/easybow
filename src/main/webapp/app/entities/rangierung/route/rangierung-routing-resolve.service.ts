import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRangierung, Rangierung } from '../rangierung.model';
import { RangierungService } from '../service/rangierung.service';

@Injectable({ providedIn: 'root' })
export class RangierungRoutingResolveService implements Resolve<IRangierung> {
  constructor(protected service: RangierungService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRangierung> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rangierung: HttpResponse<Rangierung>) => {
          if (rangierung.body) {
            return of(rangierung.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rangierung());
  }
}
