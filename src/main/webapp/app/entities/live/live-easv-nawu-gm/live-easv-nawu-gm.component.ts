import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICompetition } from '../../competition/competition.model';
import { RankingListService } from '../../competition/service/rankingList.service';
import { AccountService } from '../../../core/auth/account.service';
import { IGroupRankingList } from '../../competition/groupRankingList.model';
import { IGroupAthleteResult } from '../../competition/groupAthleteResult.model';

@Component({
  selector: 'jhi-view-easv-nawu-gm',
  templateUrl: './live-easv-nawu-gm.component.html',
  styleUrls: ['./live-easv-nawu-gm.component.scss'],
})
export class LiveEasvNawuGmComponent implements OnInit {
  competition?: ICompetition | null;
  rankingList?: IGroupRankingList | null;

  rankingListView: IGroupAthleteResult[] = [];
  rank = 0;

  variable = 1;
  maxVariable = 0;

  liveUser = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private rankingListService: RankingListService,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      if (this.competition != null) {
        this.rankingListService.getGroupRankingList(this.competition, 0).subscribe(res => {
          this.maxVariable = Math.ceil(res.body!.groupAthleteResultList!.length / 4);
          this.accountService.getAuthorites().forEach(role => {
            if (role === 'ROLE_LIVE') {
              this.liveUser = true;
            }
          });
        });
        this.refreshData();
        setInterval(() => {
          this.refreshData();
        }, 10000);
      }
    });
  }

  refreshData(): void {
    if (this.competition != null) {
      this.rankingListService.getGroupRankingList(this.competition, 0).subscribe(res => {
        this.rankingList = res.body;
        if (this.liveUser) {
          if (this.rankingList!.groupAthleteResultList!.length <= 4) {
            this.rankingListView = this.rankingList!.groupAthleteResultList!;
          } else {
            if (this.variable === 1) {
              this.rankingListView = this.rankingList!.groupAthleteResultList!.slice(0, 4);
              if (this.maxVariable === 1) {
                this.variable = 1;
              } else {
                this.variable = 2;
              }
              this.rank = 0;
            } else if (this.variable === 2) {
              this.rankingListView = this.rankingList!.groupAthleteResultList!.slice(4, 8);
              if (this.maxVariable === 2) {
                this.variable = 1;
              } else {
                this.variable = 3;
              }
              this.rank = 4;
            } else if (this.variable === 3) {
              this.rankingListView = this.rankingList!.groupAthleteResultList!.slice(8, 12);
              if (this.maxVariable === 3) {
                this.variable = 1;
              } else {
                this.variable = 4;
              }
              this.rank = 8;
            } else if (this.variable === 4) {
              this.rankingListView = this.rankingList!.groupAthleteResultList!.slice(12, 16);
              if (this.maxVariable === 4) {
                this.variable = 1;
              } else {
                this.variable = 5;
              }
              this.rank = 12;
            }
          }
        } else {
          this.rankingListView = this.rankingList!.groupAthleteResultList!;
        }
      });
    }
  }
}
