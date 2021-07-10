import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISchuetze, Schuetze } from '../schuetze.model';
import { SchuetzeService } from '../service/schuetze.service';

@Injectable({ providedIn: 'root' })
export class SchuetzeRoutingResolveService implements Resolve<ISchuetze> {
  constructor(protected service: SchuetzeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchuetze> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((schuetze: HttpResponse<Schuetze>) => {
          if (schuetze.body) {
            return of(schuetze.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Schuetze());
  }
}
