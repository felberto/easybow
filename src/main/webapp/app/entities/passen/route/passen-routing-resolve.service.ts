import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPassen, Passen } from '../passen.model';
import { PassenService } from '../service/passen.service';

@Injectable({ providedIn: 'root' })
export class PassenRoutingResolveService implements Resolve<IPassen> {
  constructor(protected service: PassenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPassen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((passen: HttpResponse<Passen>) => {
          if (passen.body) {
            return of(passen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Passen());
  }
}
