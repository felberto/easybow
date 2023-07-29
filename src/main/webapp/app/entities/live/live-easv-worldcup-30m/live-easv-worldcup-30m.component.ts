import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICompetition } from '../../competition/competition.model';
import { RankingListService } from '../../competition/service/rankingList.service';
import { IRankingList } from '../../competition/rankingList.model';
import { IAthleteResult } from '../../competition/athleteResult.model';
import { AccountService } from '../../../core/auth/account.service';

@Component({
  selector: 'jhi-view-easv-worldcup-30m',
  templateUrl: './live-easv-worldcup-30m.component.html',
  styleUrls: ['./live-easv-worldcup-30m.component.scss'],
})
export class LiveEasvWorldcup30mComponent implements OnInit {
  competition?: ICompetition | null;
  rankingList?: IRankingList | null;

  rankingListView: IAthleteResult[] = [];
  rank = 0;

  variable = 1;
  maxVariable = 0;

  liveUser = false;

  activeItemIndex = 0;
  competitionType = 1;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private rankingListService: RankingListService,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      if (this.competition != null) {
        this.rankingListService.getRankingList(this.competition, this.competitionType).subscribe(res => {
          this.maxVariable = Math.ceil(res.body!.athleteResultList!.length / 15);
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
      console.log(this.competitionType);
      this.rankingListService.getRankingList(this.competition, this.competitionType).subscribe(res => {
        console.log(res.body);
        this.rankingList = res.body;
        if (this.liveUser) {
          if (this.rankingList!.athleteResultList!.length <= 15) {
            this.rankingListView = this.rankingList!.athleteResultList!;
          } else {
            if (this.variable === 1) {
              this.rankingListView = this.rankingList!.athleteResultList!.slice(0, 15);
              if (this.maxVariable === 1) {
                this.variable = 1;
              } else {
                this.variable = 2;
              }
              this.rank = 0;
            } else if (this.variable === 2) {
              this.rankingListView = this.rankingList!.athleteResultList!.slice(15, 30);
              if (this.maxVariable === 2) {
                this.variable = 1;
              } else {
                this.variable = 3;
              }
              this.rank = 15;
            } else if (this.variable === 3) {
              this.rankingListView = this.rankingList!.athleteResultList!.slice(30, 45);
              if (this.maxVariable === 3) {
                this.variable = 1;
              } else {
                this.variable = 4;
              }
              this.rank = 30;
            } else if (this.variable === 4) {
              this.rankingListView = this.rankingList!.athleteResultList!.slice(30, 45);
              if (this.maxVariable === 4) {
                this.variable = 1;
              } else {
                this.variable = 5;
              }
              this.rank = 45;
            }
          }
        } else {
          this.rankingListView = this.rankingList!.athleteResultList!;
        }
      });
    }
  }
}
