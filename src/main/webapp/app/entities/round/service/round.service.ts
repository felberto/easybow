import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getRoundIdentifier, IRound } from '../round.model';

export type EntityResponseType = HttpResponse<IRound>;
export type EntityArrayResponseType = HttpResponse<IRound[]>;

@Injectable({ providedIn: 'root' })
export class RoundService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/rounds');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(round: IRound): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(round);
    return this.http
      .post<IRound>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(round: IRound): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(round);
    return this.http
      .put<IRound>(`${this.resourceUrl}/${getRoundIdentifier(round) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(round: IRound): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(round);
    return this.http
      .patch<IRound>(`${this.resourceUrl}/${getRoundIdentifier(round) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRound>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByRoundAndCompetition(round: number, id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRound>(`${this.resourceUrl}/${round}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByCompetition(id: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IRound[]>(`${this.resourceUrl}/competition/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRound[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRoundToCollectionIfMissing(roundCollection: IRound[], ...roundsToCheck: (IRound | null | undefined)[]): IRound[] {
    const rounds: IRound[] = roundsToCheck.filter(isPresent);
    if (rounds.length > 0) {
      const roundCollectionIdentifiers = roundCollection.map(roundItem => getRoundIdentifier(roundItem)!);
      const roundsToAdd = rounds.filter(roundItem => {
        const roundIdentifier = getRoundIdentifier(roundItem);
        if (roundIdentifier == null || roundCollectionIdentifiers.includes(roundIdentifier)) {
          return false;
        }
        roundCollectionIdentifiers.push(roundIdentifier);
        return true;
      });
      return [...roundsToAdd, ...roundCollection];
    }
    return roundCollection;
  }

  protected convertDateFromClient(round: IRound): IRound {
    return Object.assign({}, round, {
      date: round.date?.isValid() ? round.date.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? dayjs(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((round: IRound) => {
        round.date = round.date ? dayjs(round.date) : undefined;
      });
    }
    return res;
  }
}
