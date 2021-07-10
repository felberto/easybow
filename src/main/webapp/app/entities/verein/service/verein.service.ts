import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVerein, getVereinIdentifier } from '../verein.model';

export type EntityResponseType = HttpResponse<IVerein>;
export type EntityArrayResponseType = HttpResponse<IVerein[]>;

@Injectable({ providedIn: 'root' })
export class VereinService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vereins');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(verein: IVerein): Observable<EntityResponseType> {
    return this.http.post<IVerein>(this.resourceUrl, verein, { observe: 'response' });
  }

  update(verein: IVerein): Observable<EntityResponseType> {
    return this.http.put<IVerein>(`${this.resourceUrl}/${getVereinIdentifier(verein) as number}`, verein, { observe: 'response' });
  }

  partialUpdate(verein: IVerein): Observable<EntityResponseType> {
    return this.http.patch<IVerein>(`${this.resourceUrl}/${getVereinIdentifier(verein) as number}`, verein, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVerein>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVerein[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVereinToCollectionIfMissing(vereinCollection: IVerein[], ...vereinsToCheck: (IVerein | null | undefined)[]): IVerein[] {
    const vereins: IVerein[] = vereinsToCheck.filter(isPresent);
    if (vereins.length > 0) {
      const vereinCollectionIdentifiers = vereinCollection.map(vereinItem => getVereinIdentifier(vereinItem)!);
      const vereinsToAdd = vereins.filter(vereinItem => {
        const vereinIdentifier = getVereinIdentifier(vereinItem);
        if (vereinIdentifier == null || vereinCollectionIdentifiers.includes(vereinIdentifier)) {
          return false;
        }
        vereinCollectionIdentifiers.push(vereinIdentifier);
        return true;
      });
      return [...vereinsToAdd, ...vereinCollection];
    }
    return vereinCollection;
  }
}
