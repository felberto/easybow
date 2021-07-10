import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWettkampf, Wettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';

@Injectable({ providedIn: 'root' })
export class WettkampfRoutingResolveService implements Resolve<IWettkampf> {
  constructor(protected service: WettkampfService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWettkampf> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((wettkampf: HttpResponse<Wettkampf>) => {
          if (wettkampf.body) {
            return of(wettkampf.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Wettkampf());
  }
}
