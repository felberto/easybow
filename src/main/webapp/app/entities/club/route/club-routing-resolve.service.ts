import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Club, IClub } from '../club.model';
import { ClubService } from '../service/club.service';

@Injectable({ providedIn: 'root' })
export class ClubRoutingResolveService implements Resolve<IClub> {
  constructor(protected service: ClubService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClub> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((club: HttpResponse<Club>) => {
          if (club.body) {
            return of(club.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Club());
  }
}
