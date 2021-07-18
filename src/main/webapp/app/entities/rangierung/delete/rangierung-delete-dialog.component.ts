import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRangierung } from '../rangierung.model';
import { RangierungService } from '../service/rangierung.service';

@Component({
  templateUrl: './rangierung-delete-dialog.component.html',
})
export class RangierungDeleteDialogComponent {
  rangierung?: IRangierung;

  constructor(protected rangierungService: RangierungService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rangierungService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
