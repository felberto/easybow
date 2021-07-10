import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGruppen, Gruppen } from '../gruppen.model';
import { GruppenService } from '../service/gruppen.service';

@Injectable({ providedIn: 'root' })
export class GruppenRoutingResolveService implements Resolve<IGruppen> {
  constructor(protected service: GruppenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGruppen> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((gruppen: HttpResponse<Gruppen>) => {
          if (gruppen.body) {
            return of(gruppen.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Gruppen());
  }
}
