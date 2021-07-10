import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVerband, Verband } from '../verband.model';
import { VerbandService } from '../service/verband.service';

@Injectable({ providedIn: 'root' })
export class VerbandRoutingResolveService implements Resolve<IVerband> {
  constructor(protected service: VerbandService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVerband> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((verband: HttpResponse<Verband>) => {
          if (verband.body) {
            return of(verband.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Verband());
  }
}
