import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { getWettkampfIdentifier, IWettkampf } from '../../wettkampf/wettkampf.model';
import { EntityResponseType } from '../../wettkampf/service/wettkampf.service';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';

@Injectable({
  providedIn: 'root',
})
export class RanglisteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rangliste');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  createRangliste(wettkampf: IWettkampf, runden: Array<number>): Observable<EntityResponseType> {
    return this.http.post<IWettkampf>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, runden, {
      observe: 'response',
    });
  }
}
