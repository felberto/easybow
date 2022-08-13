import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getCompetitionIdentifier, ICompetition } from '../competition.model';

export type EntityResponseType = HttpResponse<ICompetition>;
export type EntityArrayResponseType = HttpResponse<ICompetition[]>;

@Injectable({ providedIn: 'root' })
export class CompetitionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/competitions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  exportData(competition: ICompetition): Observable<Blob> {
    const headers = new HttpHeaders().set('Accept', 'application/json');
    return this.http.post<Blob>(`${this.resourceUrl}/export`, competition, { headers, responseType: 'blob' as 'json' });
  }

  importData(string: string): Observable<HttpResponse<boolean>> {
    return this.http.post<boolean>(`${this.resourceUrl}/import`, string, { observe: 'response' });
  }

  create(competition: ICompetition): Observable<EntityResponseType> {
    return this.http.post<ICompetition>(this.resourceUrl, competition, { observe: 'response' });
  }

  update(competition: ICompetition): Observable<EntityResponseType> {
    return this.http.put<ICompetition>(`${this.resourceUrl}/${getCompetitionIdentifier(competition) as number}`, competition, {
      observe: 'response',
    });
  }

  findByYear(year: number): Observable<HttpResponse<Array<ICompetition>>> {
    return this.http.get<Array<ICompetition>>(`${this.resourceUrl}/year/${year}`, { observe: 'response' });
  }

  partialUpdate(competition: ICompetition): Observable<EntityResponseType> {
    return this.http.patch<ICompetition>(`${this.resourceUrl}/${getCompetitionIdentifier(competition) as number}`, competition, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<HttpResponse<Array<ICompetition>>> {
    return this.http.get<Array<ICompetition>>(`${this.resourceUrl}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCompetitionToCollectionIfMissing(
    competitionCollection: ICompetition[],
    ...competitionsToCheck: (ICompetition | null | undefined)[]
  ): ICompetition[] {
    const competitions: ICompetition[] = competitionsToCheck.filter(isPresent);
    if (competitions.length > 0) {
      const competitionCollectionIdentifiers = competitionCollection.map(competitionItem => getCompetitionIdentifier(competitionItem)!);
      const competitionsToAdd = competitions.filter(competitionItem => {
        const competitionIdentifier = getCompetitionIdentifier(competitionItem);
        if (competitionIdentifier == null || competitionCollectionIdentifiers.includes(competitionIdentifier)) {
          return false;
        }
        competitionCollectionIdentifiers.push(competitionIdentifier);
        return true;
      });
      return [...competitionsToAdd, ...competitionCollection];
    }
    return competitionCollection;
  }
}
