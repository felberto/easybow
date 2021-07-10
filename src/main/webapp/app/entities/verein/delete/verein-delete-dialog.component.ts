import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVerein } from '../verein.model';
import { VereinService } from '../service/verein.service';

@Component({
  templateUrl: './verein-delete-dialog.component.html',
})
export class VereinDeleteDialogComponent {
  verein?: IVerein;

  constructor(protected vereinService: VereinService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vereinService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
