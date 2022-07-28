import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetition } from '../competition.model';
import { RankingService } from '../../ranking/service/ranking.service';
import { HttpResponse } from '@angular/common/http';
import { getRankingCriteria, IRanking } from '../../ranking/ranking.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { RankingCriteria } from '../../enumerations/rankingCriteria.model';

@Component({
  selector: 'jhi-competition-rangierung',
  templateUrl: './competition-ranking.component.html',
  styleUrls: ['./competition-ranking.component.css'],
})
export class CompetitionRankingComponent implements OnInit {
  competition!: ICompetition;

  available = ['SERIES', 'AGE', 'RESULT', 'DEEPSHOTS', 'MOUCHEN'];

  used = [''];

  constructor(protected activatedRoute: ActivatedRoute, protected rankingService: RankingService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      this.used = [];
      this.rankingService.findByCompetition(competition).subscribe((res: HttpResponse<Array<IRanking>>) => {
        const array = res.body;

        if (array !== null) {
          array.sort((n1, n2) => {
            if ((n1.position || 0) > (n2.position || 0)) {
              return 1;
            }

            if ((n1.position || 0) < (n2.position || 0)) {
              return -1;
            }

            return 0;
          });
          array.forEach(value => {
            this.used.push(getRankingCriteria(value) ?? '');
          });
        }

        this.available = this.available.filter(item => this.used.indexOf(item) < 0);
      });
    });
  }

  save(): void {
    this.rankingService.deleteAllByCompetition(this.competition).subscribe(() => {
      this.used.forEach(item => {
        switch (item) {
          case RankingCriteria.RESULT: {
            const ranking: IRanking = {
              competition: this.competition,
              position: this.used.indexOf(item) + 1,
              rankingCriteria: RankingCriteria.RESULT,
            };
            this.rankingService.create(ranking).subscribe();
            break;
          }
          case RankingCriteria.MOUCHEN: {
            const ranking: IRanking = {
              competition: this.competition,
              position: this.used.indexOf(item) + 1,
              rankingCriteria: RankingCriteria.MOUCHEN,
            };
            this.rankingService.create(ranking).subscribe();
            break;
          }
          case RankingCriteria.AGE: {
            const ranking: IRanking = {
              competition: this.competition,
              position: this.used.indexOf(item) + 1,
              rankingCriteria: RankingCriteria.AGE,
            };
            this.rankingService.create(ranking).subscribe();
            break;
          }
          case RankingCriteria.SERIES: {
            const ranking: IRanking = {
              competition: this.competition,
              position: this.used.indexOf(item) + 1,
              rankingCriteria: RankingCriteria.SERIES,
            };
            this.rankingService.create(ranking).subscribe();
            break;
          }
          case RankingCriteria.DEEPSHOTS: {
            const ranking: IRanking = {
              competition: this.competition,
              position: this.used.indexOf(item) + 1,
              rankingCriteria: RankingCriteria.DEEPSHOTS,
            };
            this.rankingService.create(ranking).subscribe();
            break;
          }
          default: {
            // statements;
            break;
          }
        }
      });
    });
  }

  drop(event: CdkDragDrop<string[]>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
  }

  previousState(): void {
    window.history.back();
  }
}
