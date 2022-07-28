import { Component, Inject } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RankingListService } from '../service/rankingList.service';
import { ICompetition } from '../competition.model';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';

@Component({
  selector: 'jhi-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss'],
})
export class ConfirmDialogComponent {
  competition?: ICompetition;

  constructor(
    protected activeModal: NgbActiveModal,
    private rankingListService: RankingListService,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  cancel(): void {
    this.activeModal.close('');
  }

  createFinal(): void {
    if (this.competition) {
      this.rankingListService.createFinal(this.competition, 99).subscribe(res => {
        if (res.ok) {
          this.notificationsService
            .show('Finalvorbereitung abgeschlossen', {
              label: 'Finalvorbereitung erfolgreich',
              status: TuiNotification.Success,
            })
            .subscribe();
        } else {
          this.notificationsService
            .show('Finalvorbereitung konnte nicht ausgeführt werden', {
              label: 'Finalvorbereitung fehlgeschlagen',
              status: TuiNotification.Error,
            })
            .subscribe();
        }
      });
      this.cancel();
    }
  }
}
