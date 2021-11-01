import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getResultateIdentifier, IResultate, Resultate } from '../resultate.model';
import { getWettkampfIdentifier, IWettkampf } from '../../wettkampf/wettkampf.model';
import { getSchuetzeIdentifier, ISchuetze } from 'app/entities/schuetze/schuetze.model';

export type EntityResponseType = HttpResponse<IResultate>;
export type EntityArrayResponseType = HttpResponse<IResultate[]>;

@Injectable({ providedIn: 'root' })
export class ResultateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/resultates');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(resultate: IResultate): Observable<EntityResponseType> {
    return this.http.post<IResultate>(this.resourceUrl, resultate, { observe: 'response' });
  }

  update(resultate: IResultate): Observable<EntityResponseType> {
    return this.http.put<IResultate>(`${this.resourceUrl}/${getResultateIdentifier(resultate) as number}`, resultate, {
      observe: 'response',
    });
  }

  partialUpdate(resultate: IResultate): Observable<EntityResponseType> {
    return this.http.patch<IResultate>(`${this.resourceUrl}/${getResultateIdentifier(resultate) as number}`, resultate, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResultate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByWettkampf(wettkampf: IWettkampf): Observable<HttpResponse<IResultate[]>> {
    return this.http.get<IResultate[]>(`${this.resourceUrl}/wettkampf/${getWettkampfIdentifier(wettkampf) as number}`, {
      observe: 'response',
    });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResultate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteBySchuetze(schuetze: ISchuetze): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/schuetze/${getSchuetzeIdentifier(schuetze) as number}`, { observe: 'response' });
  }

  addResultateToCollectionIfMissing(
    resultateCollection: IResultate[],
    ...resultatesToCheck: (IResultate | null | undefined)[]
  ): IResultate[] {
    const resultates: IResultate[] = resultatesToCheck.filter(isPresent);
    if (resultates.length > 0) {
      const resultateCollectionIdentifiers = resultateCollection.map(resultateItem => getResultateIdentifier(resultateItem)!);
      const resultatesToAdd = resultates.filter(resultateItem => {
        const resultateIdentifier = getResultateIdentifier(resultateItem);
        if (resultateIdentifier == null || resultateCollectionIdentifiers.includes(resultateIdentifier)) {
          return false;
        }
        resultateCollectionIdentifiers.push(resultateIdentifier);
        return true;
      });
      return [...resultatesToAdd, ...resultateCollection];
    }
    return resultateCollection;
  }
}
