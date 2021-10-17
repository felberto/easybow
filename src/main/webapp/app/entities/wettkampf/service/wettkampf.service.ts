import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWettkampf, getWettkampfIdentifier } from '../wettkampf.model';

export type EntityResponseType = HttpResponse<IWettkampf>;
export type EntityArrayResponseType = HttpResponse<IWettkampf[]>;

@Injectable({ providedIn: 'root' })
export class WettkampfService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/wettkampfs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(wettkampf: IWettkampf): Observable<EntityResponseType> {
    return this.http.post<IWettkampf>(this.resourceUrl, wettkampf, { observe: 'response' });
  }

  update(wettkampf: IWettkampf): Observable<EntityResponseType> {
    return this.http.put<IWettkampf>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, wettkampf, {
      observe: 'response',
    });
  }

  partialUpdate(wettkampf: IWettkampf): Observable<EntityResponseType> {
    return this.http.patch<IWettkampf>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, wettkampf, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWettkampf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<HttpResponse<Array<IWettkampf>>> {
    return this.http.get<Array<IWettkampf>>(`${this.resourceUrl}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWettkampf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWettkampfToCollectionIfMissing(
    wettkampfCollection: IWettkampf[],
    ...wettkampfsToCheck: (IWettkampf | null | undefined)[]
  ): IWettkampf[] {
    const wettkampfs: IWettkampf[] = wettkampfsToCheck.filter(isPresent);
    if (wettkampfs.length > 0) {
      const wettkampfCollectionIdentifiers = wettkampfCollection.map(wettkampfItem => getWettkampfIdentifier(wettkampfItem)!);
      const wettkampfsToAdd = wettkampfs.filter(wettkampfItem => {
        const wettkampfIdentifier = getWettkampfIdentifier(wettkampfItem);
        if (wettkampfIdentifier == null || wettkampfCollectionIdentifiers.includes(wettkampfIdentifier)) {
          return false;
        }
        wettkampfCollectionIdentifiers.push(wettkampfIdentifier);
        return true;
      });
      return [...wettkampfsToAdd, ...wettkampfCollection];
    }
    return wettkampfCollection;
  }
}
