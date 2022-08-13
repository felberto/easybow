import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getAthleteIdentifier, IAthlete } from '../athlete.model';

export type EntityResponseType = HttpResponse<IAthlete>;
export type EntityArrayResponseType = HttpResponse<IAthlete[]>;

@Injectable({ providedIn: 'root' })
export class AthleteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/athletes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(athlete: IAthlete): Observable<EntityResponseType> {
    return this.http.post<IAthlete>(this.resourceUrl, athlete, { observe: 'response' });
  }

  update(athlete: IAthlete): Observable<EntityResponseType> {
    return this.http.put<IAthlete>(`${this.resourceUrl}/${getAthleteIdentifier(athlete) as number}`, athlete, { observe: 'response' });
  }

  partialUpdate(athlete: IAthlete): Observable<EntityResponseType> {
    return this.http.patch<IAthlete>(`${this.resourceUrl}/${getAthleteIdentifier(athlete) as number}`, athlete, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAthlete>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<HttpResponse<Array<IAthlete>>> {
    return this.http.get<Array<IAthlete>>(`${this.resourceUrl}/all`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAthlete[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAthleteToCollectionIfMissing(athleteCollection: IAthlete[], ...athletesToCheck: (IAthlete | null | undefined)[]): IAthlete[] {
    const athletes: IAthlete[] = athletesToCheck.filter(isPresent);
    if (athletes.length > 0) {
      const athleteCollectionIdentifiers = athleteCollection.map(athleteItem => getAthleteIdentifier(athleteItem)!);
      const athletesToAdd = athletes.filter(athleteItem => {
        const athleteIdentifier = getAthleteIdentifier(athleteItem);
        if (athleteIdentifier == null || athleteCollectionIdentifiers.includes(athleteIdentifier)) {
          return false;
        }
        athleteCollectionIdentifiers.push(athleteIdentifier);
        return true;
      });
      return [...athletesToAdd, ...athleteCollection];
    }
    return athleteCollection;
  }
}
