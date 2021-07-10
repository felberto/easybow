import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGruppen } from '../gruppen.model';
import { GruppenService } from '../service/gruppen.service';

@Component({
  templateUrl: './gruppen-delete-dialog.component.html',
})
export class GruppenDeleteDialogComponent {
  gruppen?: IGruppen;

  constructor(protected gruppenService: GruppenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gruppenService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
