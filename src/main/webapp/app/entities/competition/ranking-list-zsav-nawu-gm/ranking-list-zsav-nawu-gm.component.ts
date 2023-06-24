import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { ICompetition } from '../competition.model';
import { RankingListService } from '../service/rankingList.service';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IGroupRankingList } from '../groupRankingList.model';
import { IGroupAthleteResult } from '../groupAthleteResult.model';
import { IRankingList } from '../rankingList.model';
import { IAthleteResult } from '../athleteResult.model';

@Component({
  selector: 'jhi-ranking-list-zsav-nawu-gm',
  templateUrl: './ranking-list-zsav-nawu-gm.component.html',
  styleUrls: ['./ranking-list-zsav-nawu-gm.component.scss'],
})
export class RankingListZsavNawuGmComponent implements OnInit {
  competition?: ICompetition | null;
  groupRankingList?: IGroupRankingList | null;
  rankingList?: IRankingList | null;

  constructor(private rankingListService: RankingListService, protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
    });
  }

  generate(type: number): void {
    if (this.competition != null) {
      this.rankingListService.getGroupRankingList(this.competition, type).subscribe(res => {
        this.groupRankingList = res.body;
        if (type === 100) {
          const list = res.body;
          let i = list!.groupAthleteResultList!.length;
          const n = 2;
          while (i--) {
            (i + 1) % n === 0 && list?.groupAthleteResultList?.splice(i, 1);
          }
          this.groupRankingList = list;
        }
      });
      if (type !== 100) {
        this.rankingListService.getRankingList(this.competition, type).subscribe(res => {
          this.rankingList = res.body;
        });
      }
    }
  }

  dropGroup(event: CdkDragDrop<Array<IGroupAthleteResult>, any>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
  }

  drop(event: CdkDragDrop<Array<IAthleteResult>, any>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
  }

  print(): void {
    if (
      this.groupRankingList !== null &&
      this.groupRankingList !== undefined &&
      this.rankingList !== null &&
      this.rankingList !== undefined
    ) {
      this.rankingListService.printGroupRankingList(this.groupRankingList).subscribe(data => {
        const blob = new Blob([data], {
          type: 'application/pdf', // must match the Accept type
        });
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.target = '_blank';
        let runde = '';
        if (this.rankingList!.type === 1) {
          runde = '1.';
        }
        if (this.rankingList!.type === 2) {
          runde = '2.';
        }
        if (this.rankingList!.type === 100) {
          runde = 'Qualifikation Final';
        }
        if (this.rankingList!.type === 99) {
          runde = 'Final';
        }
        runde = runde.concat(' Runde');
        let title = '';
        if (
          this.groupRankingList?.competition?.name !== undefined &&
          this.groupRankingList.competition.year !== undefined &&
          this.groupRankingList.competition.year !== null
        ) {
          title =
            this.groupRankingList.competition.name.toString() +
            ' ' +
            this.groupRankingList.competition.year.toString() +
            ' Gruppe' +
            ' ' +
            runde +
            '.pdf';
        }
        link.download = title;
        link.click();
        window.URL.revokeObjectURL(link.href);
      });
      if (this.groupRankingList.type !== 100) {
        this.rankingListService.printRankingList(this.rankingList).subscribe(data => {
          const blob = new Blob([data], {
            type: 'application/pdf', // must match the Accept type
          });
          const link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.target = '_blank';
          let runde = '';
          if (this.rankingList!.type === 1) {
            runde = '1.';
          }
          if (this.rankingList!.type === 2) {
            runde = '2.';
          }
          if (this.rankingList!.type === 100) {
            runde = 'Qualifikation Final';
          }
          if (this.rankingList!.type === 99) {
            runde = 'Final';
          }
          runde = runde.concat(' Runde');
          let title = '';
          if (
            this.rankingList?.competition?.name !== undefined &&
            this.rankingList.competition.year !== undefined &&
            this.rankingList.competition.year !== null
          ) {
            title =
              this.rankingList.competition.name.toString() +
              ' ' +
              this.rankingList.competition.year.toString() +
              ' Einzel' +
              ' ' +
              runde +
              '.pdf';
          }
          link.download = title;
          link.click();
          window.URL.revokeObjectURL(link.href);
        });
      }
    }
  }
}
