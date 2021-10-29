import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { getWettkampfIdentifier, IWettkampf } from '../../wettkampf/wettkampf.model';
import { IRangliste } from '../rangliste.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RanglisteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rangliste');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  getRangliste(wettkampf: IWettkampf, runden: Array<number>): Observable<HttpResponse<IRangliste>> {
    return this.http.post<IRangliste>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, runden, {
      observe: 'response',
    });
  }
}
