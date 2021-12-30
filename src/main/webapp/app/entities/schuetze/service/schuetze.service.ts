import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getSchuetzeIdentifier, ISchuetze } from '../schuetze.model';

export type EntityResponseType = HttpResponse<ISchuetze>;
export type EntityArrayResponseType = HttpResponse<ISchuetze[]>;

@Injectable({ providedIn: 'root' })
export class SchuetzeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/schuetzes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(schuetze: ISchuetze): Observable<EntityResponseType> {
    return this.http.post<ISchuetze>(this.resourceUrl, schuetze, { observe: 'response' });
  }

  update(schuetze: ISchuetze): Observable<EntityResponseType> {
    return this.http.put<ISchuetze>(`${this.resourceUrl}/${getSchuetzeIdentifier(schuetze) as number}`, schuetze, { observe: 'response' });
  }

  partialUpdate(schuetze: ISchuetze): Observable<EntityResponseType> {
    return this.http.patch<ISchuetze>(`${this.resourceUrl}/${getSchuetzeIdentifier(schuetze) as number}`, schuetze, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchuetze>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<HttpResponse<Array<ISchuetze>>> {
    return this.http.get<Array<ISchuetze>>(`${this.resourceUrl}/all`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchuetze[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSchuetzeToCollectionIfMissing(schuetzeCollection: ISchuetze[], ...schuetzesToCheck: (ISchuetze | null | undefined)[]): ISchuetze[] {
    const schuetzes: ISchuetze[] = schuetzesToCheck.filter(isPresent);
    if (schuetzes.length > 0) {
      const schuetzeCollectionIdentifiers = schuetzeCollection.map(schuetzeItem => getSchuetzeIdentifier(schuetzeItem)!);
      const schuetzesToAdd = schuetzes.filter(schuetzeItem => {
        const schuetzeIdentifier = getSchuetzeIdentifier(schuetzeItem);
        if (schuetzeIdentifier == null || schuetzeCollectionIdentifiers.includes(schuetzeIdentifier)) {
          return false;
        }
        schuetzeCollectionIdentifiers.push(schuetzeIdentifier);
        return true;
      });
      return [...schuetzesToAdd, ...schuetzeCollection];
    }
    return schuetzeCollection;
  }
}
