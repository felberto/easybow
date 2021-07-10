import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGruppen, getGruppenIdentifier } from '../gruppen.model';

export type EntityResponseType = HttpResponse<IGruppen>;
export type EntityArrayResponseType = HttpResponse<IGruppen[]>;

@Injectable({ providedIn: 'root' })
export class GruppenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/gruppens');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(gruppen: IGruppen): Observable<EntityResponseType> {
    return this.http.post<IGruppen>(this.resourceUrl, gruppen, { observe: 'response' });
  }

  update(gruppen: IGruppen): Observable<EntityResponseType> {
    return this.http.put<IGruppen>(`${this.resourceUrl}/${getGruppenIdentifier(gruppen) as number}`, gruppen, { observe: 'response' });
  }

  partialUpdate(gruppen: IGruppen): Observable<EntityResponseType> {
    return this.http.patch<IGruppen>(`${this.resourceUrl}/${getGruppenIdentifier(gruppen) as number}`, gruppen, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGruppen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGruppen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGruppenToCollectionIfMissing(gruppenCollection: IGruppen[], ...gruppensToCheck: (IGruppen | null | undefined)[]): IGruppen[] {
    const gruppens: IGruppen[] = gruppensToCheck.filter(isPresent);
    if (gruppens.length > 0) {
      const gruppenCollectionIdentifiers = gruppenCollection.map(gruppenItem => getGruppenIdentifier(gruppenItem)!);
      const gruppensToAdd = gruppens.filter(gruppenItem => {
        const gruppenIdentifier = getGruppenIdentifier(gruppenItem);
        if (gruppenIdentifier == null || gruppenCollectionIdentifiers.includes(gruppenIdentifier)) {
          return false;
        }
        gruppenCollectionIdentifiers.push(gruppenIdentifier);
        return true;
      });
      return [...gruppensToAdd, ...gruppenCollection];
    }
    return gruppenCollection;
  }
}
