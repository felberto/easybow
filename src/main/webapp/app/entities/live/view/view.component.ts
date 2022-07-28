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
  wettkampf?: ICompetition | null;
  rangliste?: IRankingList | null;

  ranglisteView: IAthleteResult[] = [];
  rang = 0;

  variable = 1;

  constructor(protected activatedRoute: ActivatedRoute, private ranglisteService: RankingListService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      if (this.wettkampf != null) {
        this.refreshData();
        setInterval(() => {
          this.refreshData();
        }, 10000);
      }
    });
  }

  refreshData(): void {
    if (this.wettkampf != null) {
      this.ranglisteService.getRangliste(this.wettkampf, 99).subscribe(res => {
        console.log(res.body);
        this.rangliste = res.body;
        if (this.rangliste!.athleteResultList!.length > 10) {
          if (this.variable === 1) {
            this.ranglisteView = this.rangliste!.athleteResultList!.slice(0, 10);
            this.variable = 2;
            this.rang = 0;
          } else {
            this.ranglisteView = this.rangliste!.athleteResultList!.slice(10, 21);
            this.variable = 1;
            this.rang = 1;
          }
        }
      });
    }
  }
}
