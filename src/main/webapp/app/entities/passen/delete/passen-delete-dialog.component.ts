import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPassen } from '../passen.model';
import { PassenService } from '../service/passen.service';

@Component({
  templateUrl: './passen-delete-dialog.component.html',
})
export class PassenDeleteDialogComponent {
  passen?: IPassen;

  constructor(protected passenService: PassenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.passenService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
