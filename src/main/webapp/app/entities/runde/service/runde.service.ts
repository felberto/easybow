import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { getRundeIdentifier, IRunde } from '../runde.model';

export type EntityResponseType = HttpResponse<IRunde>;
export type EntityArrayResponseType = HttpResponse<IRunde[]>;

@Injectable({ providedIn: 'root' })
export class RundeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/rundes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(runde: IRunde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(runde);
    return this.http
      .post<IRunde>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(runde: IRunde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(runde);
    return this.http
      .put<IRunde>(`${this.resourceUrl}/${getRundeIdentifier(runde) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(runde: IRunde): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(runde);
    return this.http
      .patch<IRunde>(`${this.resourceUrl}/${getRundeIdentifier(runde) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRunde>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByRundeAndWettkampf(runde: number, id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRunde>(`${this.resourceUrl}/${runde}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findByWettkampf(id: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<IRunde[]>(`${this.resourceUrl}/wettkampf/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRunde[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRundeToCollectionIfMissing(rundeCollection: IRunde[], ...rundesToCheck: (IRunde | null | undefined)[]): IRunde[] {
    const rundes: IRunde[] = rundesToCheck.filter(isPresent);
    if (rundes.length > 0) {
      const rundeCollectionIdentifiers = rundeCollection.map(rundeItem => getRundeIdentifier(rundeItem)!);
      const rundesToAdd = rundes.filter(rundeItem => {
        const rundeIdentifier = getRundeIdentifier(rundeItem);
        if (rundeIdentifier == null || rundeCollectionIdentifiers.includes(rundeIdentifier)) {
          return false;
        }
        rundeCollectionIdentifiers.push(rundeIdentifier);
        return true;
      });
      return [...rundesToAdd, ...rundeCollection];
    }
    return rundeCollection;
  }

  protected convertDateFromClient(runde: IRunde): IRunde {
    return Object.assign({}, runde, {
      datum: runde.datum?.isValid() ? runde.datum.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datum = res.body.datum ? dayjs(res.body.datum) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((runde: IRunde) => {
        runde.datum = runde.datum ? dayjs(runde.datum) : undefined;
      });
    }
    return res;
  }
}
