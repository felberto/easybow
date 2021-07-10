import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISchuetze, getSchuetzeIdentifier } from '../schuetze.model';

export type EntityResponseType = HttpResponse<ISchuetze>;
export type EntityArrayResponseType = HttpResponse<ISchuetze[]>;

@Injectable({ providedIn: 'root' })
export class SchuetzeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/schuetzes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(schuetze: ISchuetze): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schuetze);
    return this.http
      .post<ISchuetze>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(schuetze: ISchuetze): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schuetze);
    return this.http
      .put<ISchuetze>(`${this.resourceUrl}/${getSchuetzeIdentifier(schuetze) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(schuetze: ISchuetze): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schuetze);
    return this.http
      .patch<ISchuetze>(`${this.resourceUrl}/${getSchuetzeIdentifier(schuetze) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISchuetze>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISchuetze[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
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

  protected convertDateFromClient(schuetze: ISchuetze): ISchuetze {
    return Object.assign({}, schuetze, {
      jahrgang: schuetze.jahrgang?.isValid() ? schuetze.jahrgang.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.jahrgang = res.body.jahrgang ? dayjs(res.body.jahrgang) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((schuetze: ISchuetze) => {
        schuetze.jahrgang = schuetze.jahrgang ? dayjs(schuetze.jahrgang) : undefined;
      });
    }
    return res;
  }
}
