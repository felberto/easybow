import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Association, IAssociation } from '../association.model';
import { AssociationService } from '../service/association.service';

@Injectable({ providedIn: 'root' })
export class AssociationRoutingResolveService implements Resolve<IAssociation> {
  constructor(protected service: AssociationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAssociation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((association: HttpResponse<Association>) => {
          if (association.body) {
            return of(association.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Association());
  }
}
