import { Component, Inject } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RanglisteService } from '../service/rangliste.service';
import { IWettkampf } from '../wettkampf.model';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';

@Component({
  selector: 'jhi-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss'],
})
export class ConfirmDialogComponent {
  wettkampf?: IWettkampf;

  constructor(
    protected activeModal: NgbActiveModal,
    private ranglisteService: RanglisteService,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  cancel(): void {
    this.activeModal.close('');
  }

  createFinal(): void {
    if (this.wettkampf) {
      this.ranglisteService.createFinal(this.wettkampf, 99).subscribe(res => {
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
