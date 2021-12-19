import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { getWettkampfIdentifier, IWettkampf } from '../wettkampf.model';
import { Observable } from 'rxjs';
import { IRangliste } from '../rangliste.model';
import { DataUtils } from '../../../core/util/data-util.service';

@Injectable({
  providedIn: 'root',
})
export class RanglisteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rangliste');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
    private dataUtilService: DataUtils
  ) {}

  getRangliste(wettkampf: IWettkampf, type: number): Observable<HttpResponse<IRangliste>> {
    return this.http.post<IRangliste>(`${this.resourceUrl}/${getWettkampfIdentifier(wettkampf) as number}`, type, {
      observe: 'response',
    });
  }

  printRangliste(rangliste: IRangliste): Observable<Blob> {
    const headers = new HttpHeaders().set('Accept', 'application/pdf');
    return this.http.post<Blob>(`${this.resourceUrl}/print`, rangliste, { headers, responseType: 'blob' as 'json' });
  }
}
