import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { getCompetitionIdentifier, ICompetition } from '../competition.model';
import { Observable } from 'rxjs';
import { IRankingList } from '../rankingList.model';
import { DataUtils } from '../../../core/util/data-util.service';
import { IGroupRankingList } from '../groupRankingList.model';

@Injectable({
  providedIn: 'root',
})
export class RankingListService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
    private dataUtilService: DataUtils
  ) {}

  getRankingList(competition: ICompetition, type: number): Observable<HttpResponse<IRankingList>> {
    return this.http.post<IRankingList>(`${this.resourceUrl}/rankinglist/${getCompetitionIdentifier(competition) as number}`, type, {
      observe: 'response',
    });
  }

  getGroupRankingList(competition: ICompetition, type: number): Observable<HttpResponse<IGroupRankingList>> {
    return this.http.post<IGroupRankingList>(
      `${this.resourceUrl}/grouprankinglist/${getCompetitionIdentifier(competition) as number}`,
      type,
      {
        observe: 'response',
      }
    );
  }

  createFinal(competition: ICompetition, type: number): Observable<HttpResponse<IRankingList>> {
    return this.http.post<IRankingList>(`${this.resourceUrl}/rankinglist/final/${getCompetitionIdentifier(competition) as number}`, type, {
      observe: 'response',
    });
  }

  printRankingList(rankingList: IRankingList): Observable<Blob> {
    const headers = new HttpHeaders().set('Accept', 'application/pdf');
    return this.http.post<Blob>(`${this.resourceUrl}/rankinglist/print`, rankingList, { headers, responseType: 'blob' as 'json' });
  }

  printGroupRankingList(rankingList: IGroupRankingList): Observable<Blob> {
    const headers = new HttpHeaders().set('Accept', 'application/pdf');
    return this.http.post<Blob>(`${this.resourceUrl}/grouprankinglist/print`, rankingList, { headers, responseType: 'blob' as 'json' });
  }
}
