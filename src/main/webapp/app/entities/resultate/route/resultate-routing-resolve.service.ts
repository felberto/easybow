import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResultate, Resultate } from '../resultate.model';
import { ResultateService } from '../service/resultate.service';

@Injectable({ providedIn: 'root' })
export class ResultateRoutingResolveService implements Resolve<IResultate> {
  constructor(protected service: ResultateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resultate: HttpResponse<Resultate>) => {
          if (resultate.body) {
            return of(resultate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Resultate());
  }
}
