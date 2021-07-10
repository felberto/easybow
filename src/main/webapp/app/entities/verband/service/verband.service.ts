import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVerband, getVerbandIdentifier } from '../verband.model';

export type EntityResponseType = HttpResponse<IVerband>;
export type EntityArrayResponseType = HttpResponse<IVerband[]>;

@Injectable({ providedIn: 'root' })
export class VerbandService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/verbands');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(verband: IVerband): Observable<EntityResponseType> {
    return this.http.post<IVerband>(this.resourceUrl, verband, { observe: 'response' });
  }

  update(verband: IVerband): Observable<EntityResponseType> {
    return this.http.put<IVerband>(`${this.resourceUrl}/${getVerbandIdentifier(verband) as number}`, verband, { observe: 'response' });
  }

  partialUpdate(verband: IVerband): Observable<EntityResponseType> {
    return this.http.patch<IVerband>(`${this.resourceUrl}/${getVerbandIdentifier(verband) as number}`, verband, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVerband>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVerband[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVerbandToCollectionIfMissing(verbandCollection: IVerband[], ...verbandsToCheck: (IVerband | null | undefined)[]): IVerband[] {
    const verbands: IVerband[] = verbandsToCheck.filter(isPresent);
    if (verbands.length > 0) {
      const verbandCollectionIdentifiers = verbandCollection.map(verbandItem => getVerbandIdentifier(verbandItem)!);
      const verbandsToAdd = verbands.filter(verbandItem => {
        const verbandIdentifier = getVerbandIdentifier(verbandItem);
        if (verbandIdentifier == null || verbandCollectionIdentifiers.includes(verbandIdentifier)) {
          return false;
        }
        verbandCollectionIdentifiers.push(verbandIdentifier);
        return true;
      });
      return [...verbandsToAdd, ...verbandCollection];
    }
    return verbandCollection;
  }
}
