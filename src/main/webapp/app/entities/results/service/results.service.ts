import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getResultsIdentifier, IResults } from '../results.model';
import { getAthleteIdentifier, IAthlete } from 'app/entities/athlete/athlete.model';
import { getCompetitionIdentifier, ICompetition } from '../../competition/competition.model';
import { IClub } from '../../club/club.model';

export type EntityResponseType = HttpResponse<IResults>;
export type EntityArrayResponseType = HttpResponse<IResults[]>;

@Injectable({ providedIn: 'root' })
export class ResultsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/results');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(results: IResults): Observable<EntityResponseType> {
    return this.http.post<IResults>(this.resourceUrl, results, { observe: 'response' });
  }

  update(results: IResults): Observable<EntityResponseType> {
    return this.http.put<IResults>(`${this.resourceUrl}/${getResultsIdentifier(results) as number}`, results, {
      observe: 'response',
    });
  }

  partialUpdate(results: IResults): Observable<EntityResponseType> {
    return this.http.patch<IResults>(`${this.resourceUrl}/${getResultsIdentifier(results) as number}`, results, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IResults>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByCompetition(competition: ICompetition): Observable<HttpResponse<IResults[]>> {
    return this.http.get<IResults[]>(`${this.resourceUrl}/competition/${getCompetitionIdentifier(competition) as number}`, {
      observe: 'response',
    });
  }

  findByCompetitionAndClub(competition: ICompetition, club: IClub): Observable<HttpResponse<IResults[]>> {
    return this.http.get<IResults[]>(
      `${this.resourceUrl}/competition/${getCompetitionIdentifier(competition) as number}/club/${club.id!}`,
      {
        observe: 'response',
      }
    );
  }

  findByRoundAndCompetition(round: number, competition: ICompetition): Observable<HttpResponse<IResults[]>> {
    return this.http.get<IResults[]>(`${this.resourceUrl}/competition/${getCompetitionIdentifier(competition) as number}/round/${round}`, {
      observe: 'response',
    });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IResults[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteByAthleteAndCompetition(athlete: IAthlete, competition: ICompetition): Observable<HttpResponse<{}>> {
    return this.http.delete(
      `${this.resourceUrl}/athlete/${getAthleteIdentifier(athlete) as number}/${getCompetitionIdentifier(competition) as number}`,
      { observe: 'response' }
    );
  }

  addResultsToCollectionIfMissing(resultsCollection: IResults[], ...resultsToCheck: (IResults | null | undefined)[]): IResults[] {
    const results: IResults[] = resultsToCheck.filter(isPresent);
    if (results.length > 0) {
      const resultsCollectionIdentifiers = resultsCollection.map(resultsItem => getResultsIdentifier(resultsItem)!);
      const resultssToAdd = results.filter(resultsItem => {
        const resultsIdentifier = getResultsIdentifier(resultsItem);
        if (resultsIdentifier == null || resultsCollectionIdentifiers.includes(resultsIdentifier)) {
          return false;
        }
        resultsCollectionIdentifiers.push(resultsIdentifier);
        return true;
      });
      return [...resultssToAdd, ...resultsCollection];
    }
    return resultsCollection;
  }
}
