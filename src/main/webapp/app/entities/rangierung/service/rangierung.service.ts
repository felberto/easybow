import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRangierung, getRangierungIdentifier } from '../rangierung.model';
import { getWettkampfIdentifier, IWettkampf } from '../../wettkampf/wettkampf.model';

export type EntityResponseType = HttpResponse<IRangierung>;
export type EntityArrayResponseType = HttpResponse<IRangierung[]>;

@Injectable({ providedIn: 'root' })
export class RangierungService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rangierungs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rangierung: IRangierung): Observable<EntityResponseType> {
    return this.http.post<IRangierung>(this.resourceUrl, rangierung, { observe: 'response' });
  }

  update(rangierung: IRangierung): Observable<EntityResponseType> {
    return this.http.put<IRangierung>(`${this.resourceUrl}/${getRangierungIdentifier(rangierung) as number}`, rangierung, {
      observe: 'response',
    });
  }

  partialUpdate(rangierung: IRangierung): Observable<EntityResponseType> {
    return this.http.patch<IRangierung>(`${this.resourceUrl}/${getRangierungIdentifier(rangierung) as number}`, rangierung, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRangierung>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByWettkampf(wettkampf: IWettkampf): Observable<HttpResponse<Array<IRangierung>>> {
    return this.http.get<Array<IRangierung>>(`${this.resourceUrl}/wettkampf/${getWettkampfIdentifier(wettkampf) as number}`, {
      observe: 'response',
    });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRangierung[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRangierungToCollectionIfMissing(
    rangierungCollection: IRangierung[],
    ...rangierungsToCheck: (IRangierung | null | undefined)[]
  ): IRangierung[] {
    const rangierungs: IRangierung[] = rangierungsToCheck.filter(isPresent);
    if (rangierungs.length > 0) {
      const rangierungCollectionIdentifiers = rangierungCollection.map(rangierungItem => getRangierungIdentifier(rangierungItem)!);
      const rangierungsToAdd = rangierungs.filter(rangierungItem => {
        const rangierungIdentifier = getRangierungIdentifier(rangierungItem);
        if (rangierungIdentifier == null || rangierungCollectionIdentifiers.includes(rangierungIdentifier)) {
          return false;
        }
        rangierungCollectionIdentifiers.push(rangierungIdentifier);
        return true;
      });
      return [...rangierungsToAdd, ...rangierungCollection];
    }
    return rangierungCollection;
  }
}
