import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getGroupIdentifier, IGroup } from '../group.model';
import { getCompetitionIdentifier, ICompetition } from '../../competition/competition.model';
import { getClubIdentifier, IClub } from '../../club/club.model';

export type EntityResponseType = HttpResponse<IGroup>;
export type EntityArrayResponseType = HttpResponse<IGroup[]>;

@Injectable({ providedIn: 'root' })
export class GroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(group: IGroup): Observable<EntityResponseType> {
    return this.http.post<IGroup>(this.resourceUrl, group, { observe: 'response' });
  }

  update(group: IGroup): Observable<EntityResponseType> {
    return this.http.put<IGroup>(`${this.resourceUrl}/${getGroupIdentifier(group) as number}`, group, { observe: 'response' });
  }

  partialUpdate(group: IGroup): Observable<EntityResponseType> {
    return this.http.patch<IGroup>(`${this.resourceUrl}/${getGroupIdentifier(group) as number}`, group, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByCompetition(competition: ICompetition): Observable<HttpResponse<Array<IGroup>>> {
    return this.http.get<Array<IGroup>>(`${this.resourceUrl}/competition/${getCompetitionIdentifier(competition) as number}`, {
      observe: 'response',
    });
  }

  findByCompetitionAndClub(competition: ICompetition, club: IClub): Observable<HttpResponse<Array<IGroup>>> {
    return this.http.get<Array<IGroup>>(
      `${this.resourceUrl}/competitionclub/${getCompetitionIdentifier(competition) as number}/${getClubIdentifier(club) as number}`,
      {
        observe: 'response',
      }
    );
  }

  findByCompetitionAndClubOnlyOne(competition: ICompetition, club: IClub): Observable<HttpResponse<Array<IGroup>>> {
    return this.http.get<Array<IGroup>>(
      `${this.resourceUrl}/competitionclub/one/${getCompetitionIdentifier(competition) as number}/${getClubIdentifier(club) as number}`,
      {
        observe: 'response',
      }
    );
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGroupToCollectionIfMissing(groupCollection: IGroup[], ...groupsToCheck: (IGroup | null | undefined)[]): IGroup[] {
    const groups: IGroup[] = groupsToCheck.filter(isPresent);
    if (groups.length > 0) {
      const groupCollectionIdentifiers = groupCollection.map(groupItem => getGroupIdentifier(groupItem)!);
      const groupsToAdd = groups.filter(groupItem => {
        const groupIdentifier = getGroupIdentifier(groupItem);
        if (groupIdentifier == null || groupCollectionIdentifiers.includes(groupIdentifier)) {
          return false;
        }
        groupCollectionIdentifiers.push(groupIdentifier);
        return true;
      });
      return [...groupsToAdd, ...groupCollection];
    }
    return groupCollection;
  }
}
