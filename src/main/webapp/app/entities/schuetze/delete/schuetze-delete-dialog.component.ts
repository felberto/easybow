import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchuetze } from '../schuetze.model';
import { SchuetzeService } from '../service/schuetze.service';

@Component({
  templateUrl: './schuetze-delete-dialog.component.html',
})
export class SchuetzeDeleteDialogComponent {
  schuetze?: ISchuetze;

  constructor(protected schuetzeService: SchuetzeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.schuetzeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
