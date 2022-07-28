import { Component } from '@angular/core';
import { IRankingList } from '../rankingList.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RankingListService } from '../service/rankingList.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-rankingList-dialog',
  templateUrl: './rankingList-dialog.component.html',
  styleUrls: ['./rankingList-dialog.component.scss'],
})
export class RankingListDialogComponent {
  isDataAvailable: boolean;
  rankingList?: IRankingList | null;

  printForm = this.fb.group({
    date: [null, [Validators.required]],
    time: [null, [Validators.required]],
    ort: [null, [Validators.required]],
    anzahl: [null, [Validators.required]],
  });

  constructor(protected activeModal: NgbActiveModal, private rankingListService: RankingListService, private fb: FormBuilder) {
    this.isDataAvailable = false;
  }

  cancel(): void {
    this.activeModal.close('');
  }

  print(): void {
    this.rankingList!.vfDate = this.printForm.get('date')!.value;
    this.rankingList!.vfTime = this.printForm.get('time')!.value;
    this.rankingList!.vfOrt = this.printForm.get('ort')!.value;
    this.rankingList!.vfAnzahl = this.printForm.get('anzahl')!.value;
    if (this.rankingList !== null && this.rankingList !== undefined) {
      this.rankingListService.printRankingList(this.rankingList).subscribe(data => {
        const blob = new Blob([data], {
          type: 'application/pdf', // must match the Accept type
        });
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.target = '_blank';
        const runde = 'Qualifikation Verbändefinal';
        let title = '';
        if (
          this.rankingList?.competition?.name !== undefined &&
          this.rankingList.competition.year !== undefined &&
          this.rankingList.competition.year !== null
        ) {
          title =
            'RankingList ' +
            this.rankingList.competition.name.toString() +
            ' ' +
            this.rankingList.competition.year.toString() +
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
