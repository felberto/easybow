import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Competition, ICompetition } from '../competition.model';
import { CompetitionService } from '../service/competition.service';

@Injectable({ providedIn: 'root' })
export class CompetitionRoutingResolveService implements Resolve<ICompetition> {
  constructor(protected service: CompetitionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompetition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((competition: HttpResponse<Competition>) => {
          if (competition.body) {
            return of(competition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Competition());
  }
}
