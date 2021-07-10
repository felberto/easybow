import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVerband } from '../verband.model';
import { VerbandService } from '../service/verband.service';

@Component({
  templateUrl: './verband-delete-dialog.component.html',
})
export class VerbandDeleteDialogComponent {
  verband?: IVerband;

  constructor(protected verbandService: VerbandService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.verbandService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
