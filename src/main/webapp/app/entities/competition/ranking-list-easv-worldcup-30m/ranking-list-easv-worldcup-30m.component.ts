import { Component, OnInit } from '@angular/core';
import { IRankingList } from '../rankingList.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { IAthleteResult } from '../athleteResult.model';
import { ICompetition } from '../competition.model';
import { RankingListService } from '../service/rankingList.service';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RankingListDialogComponent } from '../rankingList-dialog/rankingList-dialog.component';

@Component({
  selector: 'jhi-ranking-list-easv-worldcup-30m',
  templateUrl: './ranking-list-easv-worldcup-30m.component.html',
  styleUrls: ['./ranking-list-easv-worldcup-30m.component.scss'],
})
export class RankingListEasvWorldcup30mComponent implements OnInit {
  competition?: ICompetition | null;
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
      this.rankingListService.getRankingList(this.competition, 1).subscribe(res => {
        this.rankingList = res.body;
      });
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
    if (this.rankingList !== null && this.rankingList !== undefined) {
      if (this.rankingList.type === 101) {
        const modalRef = this.modalService.open(RankingListDialogComponent, { size: 'xl', backdrop: 'static' });
        modalRef.componentInstance.rankingList = this.rankingList;
      } else {
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
            title = 'Results ' + this.rankingList.competition.name.toString() + ' ' + this.rankingList.competition.year.toString() + '.pdf';
          }
          link.download = title;
          link.click();
          window.URL.revokeObjectURL(link.href);
        });
      }
    }
  }
}
