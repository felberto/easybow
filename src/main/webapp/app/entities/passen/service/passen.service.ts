import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPassen, getPassenIdentifier } from '../passen.model';

export type EntityResponseType = HttpResponse<IPassen>;
export type EntityArrayResponseType = HttpResponse<IPassen[]>;

@Injectable({ providedIn: 'root' })
export class PassenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/passens');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(passen: IPassen): Observable<EntityResponseType> {
    return this.http.post<IPassen>(this.resourceUrl, passen, { observe: 'response' });
  }

  update(passen: IPassen): Observable<EntityResponseType> {
    return this.http.put<IPassen>(`${this.resourceUrl}/${getPassenIdentifier(passen) as number}`, passen, { observe: 'response' });
  }

  partialUpdate(passen: IPassen): Observable<EntityResponseType> {
    return this.http.patch<IPassen>(`${this.resourceUrl}/${getPassenIdentifier(passen) as number}`, passen, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPassen>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPassen[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPassenToCollectionIfMissing(passenCollection: IPassen[], ...passensToCheck: (IPassen | null | undefined)[]): IPassen[] {
    const passens: IPassen[] = passensToCheck.filter(isPresent);
    if (passens.length > 0) {
      const passenCollectionIdentifiers = passenCollection.map(passenItem => getPassenIdentifier(passenItem)!);
      const passensToAdd = passens.filter(passenItem => {
        const passenIdentifier = getPassenIdentifier(passenItem);
        if (passenIdentifier == null || passenCollectionIdentifiers.includes(passenIdentifier)) {
          return false;
        }
        passenCollectionIdentifiers.push(passenIdentifier);
        return true;
      });
      return [...passensToAdd, ...passenCollection];
    }
    return passenCollection;
  }
}
