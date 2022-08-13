import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICompetition } from '../../competition/competition.model';
import { RankingListService } from '../../competition/service/rankingList.service';
import { IRankingList } from '../../competition/rankingList.model';
import { IAthleteResult } from '../../competition/athleteResult.model';

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

  variable = 1;

  constructor(protected activatedRoute: ActivatedRoute, private rankingListService: RankingListService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      if (this.competition != null) {
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
        if (this.rankingList!.athleteResultList!.length > 10) {
          if (this.variable === 1) {
            this.rankingListView = this.rankingList!.athleteResultList!.slice(0, 10);
            this.variable = 2;
            this.rank = 0;
          } else {
            this.rankingListView = this.rankingList!.athleteResultList!.slice(10, 21);
            this.variable = 1;
            this.rank = 1;
          }
        }
      });
    }
  }
}
