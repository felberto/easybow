import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getSeriesIdentifier, ISeries } from '../series.model';

export type EntityResponseType = HttpResponse<ISeries>;
export type EntityArrayResponseType = HttpResponse<ISeries[]>;

@Injectable({ providedIn: 'root' })
export class SeriesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/series');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(series: ISeries): Observable<EntityResponseType> {
    return this.http.post<ISeries>(this.resourceUrl, series, { observe: 'response' });
  }

  update(series: ISeries): Observable<EntityResponseType> {
    return this.http.put<ISeries>(`${this.resourceUrl}/${getSeriesIdentifier(series) as number}`, series, { observe: 'response' });
  }

  partialUpdate(series: ISeries): Observable<EntityResponseType> {
    return this.http.patch<ISeries>(`${this.resourceUrl}/${getSeriesIdentifier(series) as number}`, series, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISeries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISeries[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSeriesToCollectionIfMissing(seriesCollection: ISeries[], ...seriesToCheck: (ISeries | null | undefined)[]): ISeries[] {
    const series: ISeries[] = seriesToCheck.filter(isPresent);
    if (series.length > 0) {
      const seriesCollectionIdentifiers = seriesCollection.map(seriesItem => getSeriesIdentifier(seriesItem)!);
      const seriesToAdd = series.filter(seriesItem => {
        const seriesIdentifier = getSeriesIdentifier(seriesItem);
        if (seriesIdentifier == null || seriesCollectionIdentifiers.includes(seriesIdentifier)) {
          return false;
        }
        seriesCollectionIdentifiers.push(seriesIdentifier);
        return true;
      });
      return [...seriesToAdd, ...seriesCollection];
    }
    return seriesCollection;
  }
}
