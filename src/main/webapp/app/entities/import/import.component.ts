import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CompetitionService } from '../competition/service/competition.service';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';

@Component({
  selector: 'jhi-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss'],
})
export class ImportComponent {
  dataForm = new FormGroup({
    data: new FormControl('', Validators.required),
  });

  constructor(
    private wettkampfService: CompetitionService,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  import(): void {
    this.wettkampfService.importData(this.dataForm.get('data')!.value).subscribe(res => {
      if (res.ok) {
        this.notificationsService
          .show('Import abgeschlossen', {
            label: 'Import von Wettkampf erfolgreich',
            status: TuiNotification.Success,
          })
          .subscribe();
      } else {
        this.notificationsService
          .show('Import konnte nicht ausgeführt werden', {
            label: 'Wettkampf importieren fehlgeschlagen',
            status: TuiNotification.Error,
          })
          .subscribe();
      }
    });
  }
}
