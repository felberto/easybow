import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWettkampf, getWettkampfIdentifier } from '../wettkampf.model';

export type EntityResponseType = HttpResponse<IWettkampf>;
export type EntityArrayResponseType = HttpResponse<IWettkampf[]>;

@Injectable({ providedIn: 'root' })
export class WettkampfService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/wettkampfs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(wettkampf: IWettkampf): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wettkampf);
    return this.http
      .post<IWettkampf>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(wettkampf: IWettkampf): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wettkampf);
    return this.http
      .put<IWettkampf>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(wettkampf: IWettkampf): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wettkampf);
    return this.http
      .patch<IWettkampf>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWettkampf>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWettkampf[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addWettkampfToCollectionIfMissing(
    wettkampfCollection: IWettkampf[],
    ...wettkampfsToCheck: (IWettkampf | null | undefined)[]
  ): IWettkampf[] {
    const wettkampfs: IWettkampf[] = wettkampfsToCheck.filter(isPresent);
    if (wettkampfs.length > 0) {
      const wettkampfCollectionIdentifiers = wettkampfCollection.map(wettkampfItem => getWettkampfIdentifier(wettkampfItem)!);
      const wettkampfsToAdd = wettkampfs.filter(wettkampfItem => {
        const wettkampfIdentifier = getWettkampfIdentifier(wettkampfItem);
        if (wettkampfIdentifier == null || wettkampfCollectionIdentifiers.includes(wettkampfIdentifier)) {
          return false;
        }
        wettkampfCollectionIdentifiers.push(wettkampfIdentifier);
        return true;
      });
      return [...wettkampfsToAdd, ...wettkampfCollection];
    }
    return wettkampfCollection;
  }

  protected convertDateFromClient(wettkampf: IWettkampf): IWettkampf {
    return Object.assign({}, wettkampf, {
      jahr: wettkampf.jahr?.isValid() ? wettkampf.jahr.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jahr = res.body.jahr ? dayjs(res.body.jahr) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((wettkampf: IWettkampf) => {
        wettkampf.jahr = wettkampf.jahr ? dayjs(wettkampf.jahr) : undefined;
      });
    }
    return res;
  }
}
