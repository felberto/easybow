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
  selector: 'jhi-ranking-list-easv-verbaendefinal',
  templateUrl: './ranking-list-easv-verbaendefinal.component.html',
  styleUrls: ['./ranking-list-easv-verbaendefinal.component.scss'],
})
export class RankingListEasvVerbaendefinalComponent implements OnInit {
  competition?: ICompetition | null;
  groupRankingList?: IGroupRankingList | null;
  rankingList?: IRankingList | null;

  constructor(private rankingListService: RankingListService, protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      this.generate();
    });
  }

  generate(): void {
    if (this.competition != null) {
      this.rankingListService.getGroupRankingList(this.competition, 1).subscribe(res => {
        this.groupRankingList = res.body;
      });
      this.rankingListService.getRankingList(this.competition, 2).subscribe(res => {
        this.rankingList = res.body;
      });
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
            ' Verband' +
            '.pdf';
        }
        link.download = title;
        link.click();
        window.URL.revokeObjectURL(link.href);
      });
      this.rankingListService.printRankingList(this.rankingList).subscribe(data => {
        const blob = new Blob([data], {
          type: 'application/pdf', // must match the Accept type
        });
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.target = '_blank';
        let title = '';
        if (
          this.rankingList?.competition?.name !== undefined &&
          this.rankingList.competition.year !== undefined &&
          this.rankingList.competition.year !== null
        ) {
          title = this.rankingList.competition.name.toString() + ' ' + this.rankingList.competition.year.toString() + ' Einzel' + '.pdf';
        }
        link.download = title;
        link.click();
        window.URL.revokeObjectURL(link.href);
      });
    }
  }
}
