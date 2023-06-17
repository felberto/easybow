import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICompetition } from '../../competition/competition.model';
import { RankingListService } from '../../competition/service/rankingList.service';
import { IRankingList } from '../../competition/rankingList.model';
import { IAthleteResult } from '../../competition/athleteResult.model';
import { AccountService } from '../../../core/auth/account.service';

@Component({
  selector: 'jhi-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss'],
})
export class ViewComponent implements OnInit {
  competition?: ICompetition | null;
  rankingList?: IRankingList | null;

  rankingListView: IAthleteResult[] = [];
  rank = 0;

  liveUser = false;

  variable = 1;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private rankingListService: RankingListService,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      if (this.competition != null) {
        this.accountService.getAuthorites().forEach(role => {
          if (role === 'ROLE_LIVE') {
            this.liveUser = true;
          }
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
      this.rankingListService.getRankingList(this.competition, 99).subscribe(res => {
        console.log(res.body);
        this.rankingList = res.body;
        if (this.liveUser) {
          this.rankingListView = this.rankingList!.athleteResultList!;
        } else {
          this.rankingListView = this.rankingList!.athleteResultList!;
        }
      });
    }
  }
}
