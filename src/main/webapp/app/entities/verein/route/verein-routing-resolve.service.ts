import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVerein, Verein } from '../verein.model';
import { VereinService } from '../service/verein.service';

@Injectable({ providedIn: 'root' })
export class VereinRoutingResolveService implements Resolve<IVerein> {
  constructor(protected service: VereinService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVerein> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((verein: HttpResponse<Verein>) => {
          if (verein.body) {
            return of(verein.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Verein());
  }
}
